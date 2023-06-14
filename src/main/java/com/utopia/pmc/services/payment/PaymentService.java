package com.utopia.pmc.services.payment;

import java.io.UnsupportedEncodingException;

import com.utopia.pmc.data.dto.request.payment.PaymentRequest;
import com.utopia.pmc.data.dto.response.payment.PaymentResponse;

public interface PaymentService {
    public PaymentResponse createdPayment(PaymentRequest paymentRequest) throws UnsupportedEncodingException;
    public PaymentResponse checkPaymentStatus(String orderId, String responseCode,String transactionNo, String transDate) throws Exception;
}
