package com.utopia.pmc.services.payment.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia.pmc.config.VNPayConfig;
import com.utopia.pmc.data.constants.statuses.TransactionStatus;
import com.utopia.pmc.data.dto.request.payment.PaymentRequest;
import com.utopia.pmc.data.dto.response.payment.PaymentResponse;
import com.utopia.pmc.data.entities.Transaction;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.TransactionRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.services.authenticate.SecurityContextService;
import com.utopia.pmc.services.payment.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private SecurityContextService securityContextService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private Message message;

    @Override
    public PaymentResponse createdPayment(PaymentRequest paymentRequest) throws UnsupportedEncodingException {
        User user = securityContextService.getCurrentUser();
        String orderType = paymentRequest.getOrderType();
        long amount = paymentRequest.getAmount() * 100;
        String bankCode = paymentRequest.getBankCode();

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        // String vnp_IpAddr = VNPayConfig.getIpAddress(req);
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = paymentRequest.getLanguage();
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_Returnurl);
        // vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        Transaction transaction = Transaction
                .builder()
                .amount(amount)
                .transactioncreatedDate(vnp_CreateDate)
                .user(user)
                .transactionId(vnp_TxnRef)
                .bankCode(bankCode)
                .transactionStatus(TransactionStatus.PENDING)
                .paymentPlan(orderType)
                .expireDate(vnp_ExpireDate)
                .build();

        PaymentResponse paymentResponse = PaymentResponse
                .builder()
                .code("200")
                .message("success")
                .data(paymentUrl)
                .build();
        transactionRepository.save(transaction);
        return paymentResponse;
    }

    @Override
    public PaymentResponse checkPaymentStatus(String orderId, String responeCode,String transactionNo, String transDate) throws Exception {
        Transaction transaction = transactionRepository.findByTransactionId(orderId)
                .orElseThrow(() -> new BadRequestException(message.objectNotFoundByIdMessage("Transaction", orderId)));
        String vnp_RequestId = VNPayConfig.getRandomNumber(8);
        String vnp_Version = "2.1.0";
        String vnp_Command = "querydr";
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String vnp_TxnRef = orderId;
        String vnp_OrderInfo = "Kiem tra ket qua GD OrderId:" + vnp_TxnRef;
        String vnp_TransactionNo = transactionNo;
        String vnp_TransDate = transDate;

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        // String vnp_IpAddr = Config.getIpAddress(req);

        Map vnp_Params = new HashMap<>();

        vnp_Params.put("vnp_RequestId", vnp_RequestId);
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_TransactionNo", vnp_TransactionNo);
        vnp_Params.put("vnp_TransactionDate", vnp_TransDate);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        // vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);

        String hash_Data = vnp_RequestId + "|" + vnp_Version + "|" + vnp_Command + "|" + vnp_TmnCode + "|" + vnp_TxnRef
                + "|" + vnp_TransDate + "|" + vnp_CreateDate + "|" + "|" + vnp_OrderInfo;

        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hash_Data.toString());

        vnp_Params.put("vnp_SecureHash", vnp_SecureHash);

        URL url = new URL(VNPayConfig.vnp_apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(vnp_Params.toString());
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + vnp_Params);
        System.out.println("Response Code : " + responseCode);
        transaction.setTransactionPaymentDate(vnp_CreateDate);
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(transaction);
        return PaymentResponse
                .builder()
                .code(String.valueOf(responseCode))
                .data(vnp_OrderInfo)
                .build();
    }

}
