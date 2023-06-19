package com.utopia.pmc.controller.vnpay;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.utopia.pmc.controller.redirect.RedirectController;
import com.utopia.pmc.data.dto.request.payment.PaymentRequest;
import com.utopia.pmc.data.dto.response.payment.PaymentResponse;
import com.utopia.pmc.data.dto.response.transaction.TransactionResponse;
import com.utopia.pmc.exceptions.BadRequestException;
import com.utopia.pmc.exceptions.ForbiddenException;
import com.utopia.pmc.services.payment.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/transaction")
public class VNPayController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RedirectController redirectController;

    @Operation(summary = "Create payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create successfull.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))}),
            @ApiResponse(responseCode = "400", description = "User not valid.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class))}),
            @ApiResponse(responseCode = "401", description = "Username or password is incorrect. Please try again", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenException.class))})
    })
    @PostMapping()
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest) throws UnsupportedEncodingException {
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
            paymentService.createdPayment(paymentRequest)
        );
    }

//     @Operation(summary = "Check payment")
//     @ApiResponses(value = {
//             @ApiResponse(responseCode = "200", description = "Check successfull.", content = {
//                     @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))}),
//             @ApiResponse(responseCode = "400", description = "User not valid.", content = {
//                     @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class))}),
//             @ApiResponse(responseCode = "401", description = "Username or password is incorrect. Please try again", content = {
//                     @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenException.class))})
//     })
//     @GetMapping("/check")
//     public void transaction(
//         @RequestParam(value = "vnp_TxnRef") String orderId,
//         @RequestParam(value = "vnp_BankTranNo") String transactionNo,
//         @RequestParam(value = "vnp_PayDate") String transDate,
//         @RequestParam(value = "vnp_ResponseCode") String responseCode,
//         Model model
//     ) throws Exception{
//         TransactionResponse transactionResponse = paymentService.checkPaymentStatus(orderId, responseCode, transactionNo, transDate);
//         redirectController.transactionPage(model,transactionResponse);
//     }
}
