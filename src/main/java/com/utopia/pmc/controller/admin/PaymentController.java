package com.utopia.pmc.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.pmc.data.dto.request.payment.NewPaymentRequest;
import com.utopia.pmc.data.dto.response.payment.PaymentPlanResponse;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.services.payment.PaymentPlansService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/admin/payment")
public class PaymentController {
    @Autowired
    private PaymentPlansService paymentPlansService;

    @Operation(summary = "Create new payment plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create successfull.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentPlanResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Create fail.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class))})
    })
    @PostMapping
    public ResponseEntity<Void> createNewPaymenPlan(@RequestBody NewPaymentRequest newPaymentRequest) {
        paymentPlansService.createNewPaymentPlan(newPaymentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
