package com.utopia.pmc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.pmc.data.dto.response.payment.PaymentPlanResponse;
import com.utopia.pmc.services.payment.PaymentPlansService;

@RestController
@RequestMapping("/api/payment")
public class PaymentPlanController {

    @Autowired
    private PaymentPlansService paymentPlansService;

    @GetMapping()
    public ResponseEntity<List<PaymentPlanResponse>> getAllPaymentPlan() {
        return ResponseEntity.status(HttpStatus.OK).body(
                paymentPlansService.getAllPaymentPlans());
    }
    
}
