package com.utopia.pmc.services.payment.impl;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.utopia.pmc.config.VNPayConfig;
import com.utopia.pmc.data.constants.statuses.TransactionStatus;
import com.utopia.pmc.data.dto.request.payment.PaymentRequest;
import com.utopia.pmc.data.dto.response.payment.PaymentResponse;
import com.utopia.pmc.data.dto.response.transaction.TransactionResponse;
import com.utopia.pmc.data.entities.PaymentPlan;
import com.utopia.pmc.data.entities.Transaction;
import com.utopia.pmc.data.entities.User;
import com.utopia.pmc.data.repositories.PaymentPlanRepository;
import com.utopia.pmc.data.repositories.TransactionRepository;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.message.Message;
import com.utopia.pmc.mappers.TransactionMapper;
import com.utopia.pmc.services.authenticate.SecurityContextService;
import com.utopia.pmc.services.payment.PaymentService;
import com.utopia.pmc.services.user.UserService;
import com.utopia.pmc.utils.ConvertStringToLocalDateTime;
import com.utopia.pmc.utils.EnvironmentVariable;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private EnvironmentVariable environmentVariable;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PaymentPlanRepository paymentPlanRepository;
    @Autowired
    private SecurityContextService securityContextService;
    @Autowired
    private UserService userService;
    @Autowired
    private Message message;
    @Autowired
    private ConvertStringToLocalDateTime convertStringToLocalDateTime;
    @Autowired
    private TransactionMapper transactionMapper;

    @Transactional
    @Override
    public PaymentResponse createdPayment(PaymentRequest paymentRequest) throws UnsupportedEncodingException {
        User user = securityContextService.getCurrentUser();
        PaymentPlan paymentPlan = paymentPlanRepository.findById(paymentRequest.getPaymentId())
                .orElseThrow(() -> new BadRequestException(
                        message.objectNotFoundByIdMessage("Payment plan", paymentRequest.getPaymentId())));
        String orderType = paymentPlan.getName();
        long amount = (long) (paymentPlan.getMoney() * 100);
        String bankCode = paymentRequest.getBankCode();

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
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
        String vpn_ReturnUrl = environmentVariable.getBaseurl() + "transaction_page";
        vnp_Params.put("vnp_ReturnUrl", vpn_ReturnUrl);

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
        // set transaction
        LocalDate createdDate = convertStringToLocalDateTime.convertStringToLocalDate(vnp_CreateDate);
        LocalDate expireDate = convertStringToLocalDateTime.convertStringToLocalDate(vnp_ExpireDate);

        Transaction transaction = Transaction
                .builder()
                .amount(amount)
                .transactioncreatedDate(createdDate)
                .user(user)
                .transactionId(vnp_TxnRef)
                .bankCode(bankCode)
                .transactionStatus(TransactionStatus.PENDING)
                .paymentPlan(orderType)
                .expireDate(expireDate)
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

    @Transactional
    @Override
    public TransactionResponse checkPaymentStatus(String orderId, String responeCode, String transactionNo,
            String transDate) throws Exception {

        Transaction transaction = transactionRepository.findByTransactionId(orderId)
                .orElseThrow(() -> new BadRequestException(message.objectNotFoundByIdMessage("Transaction", orderId)));

        PaymentPlan paymentPlan = paymentPlanRepository.findByName(transaction.getPaymentPlan())
                .orElseThrow(() -> new BadRequestException(
                        message.objectNotFoundByIdMessage("Payment plan", transaction.getPaymentPlan())));

        String vnp_TransDate = transDate;
        TransactionStatus transactionStatus = TransactionStatus.SUCCESS;

        if (!responeCode.equals("00")) {
            transactionStatus = TransactionStatus.FAIL;
        }

        URL url = new URL(VNPayConfig.vnp_apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        LocalDate transLocalDate = convertStringToLocalDateTime.convertStringToLocalDate(vnp_TransDate);

        transaction.setTransactionId(orderId);
        transaction.setTransactionPaymentDate(transLocalDate);
        transaction.setTransactionStatus(transactionStatus);
        transactionRepository.save(transaction);

        User user = transaction.getUser();
        userService.upgradePaymenPlan(user, paymentPlan);

        return transactionMapper.mapEntityToDto(transaction);
    }

}
