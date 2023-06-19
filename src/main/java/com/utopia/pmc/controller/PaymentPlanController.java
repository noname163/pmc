package com.utopia.pmc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.pmc.data.dto.response.payment.PaymentPlanResponse;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.services.payment.PaymentPlansService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/payment")
public class PaymentPlanController {

    @Autowired
    private PaymentPlansService paymentPlansService;

    @Operation(summary = "Get all payment plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get successfull.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentPlanResponse.class))}),
            @ApiResponse(responseCode = "400", description = "User not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class))})
    })
    @GetMapping()
    public ResponseEntity<List<PaymentPlanResponse>> getAllPaymentPlan() {
        return ResponseEntity.status(HttpStatus.OK).body(
                paymentPlansService.getAllPaymentPlans());
    }
    
}
