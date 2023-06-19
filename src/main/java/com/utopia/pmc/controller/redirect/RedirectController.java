package com.utopia.pmc.controller.redirect;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.utopia.pmc.data.dto.response.payment.PaymentResponse;
import com.utopia.pmc.data.dto.response.transaction.TransactionResponse;
import com.utopia.pmc.services.payment.PaymentPlansService;
import com.utopia.pmc.services.payment.PaymentService;
import com.utopia.pmc.ui.UI;

@Controller
public class RedirectController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/transaction_page")
    @ResponseBody
    public String transactionPage(@RequestParam(value = "vnp_TxnRef") String orderId,
        @RequestParam(value = "vnp_BankTranNo") String transactionNo,
        @RequestParam(value = "vnp_PayDate") String transDate,
        @RequestParam(value = "vnp_ResponseCode") String responseCode,
        HttpSession session) throws Exception {
            TransactionResponse transactionResponse = paymentService.checkPaymentStatus(orderId, responseCode, transactionNo, transDate);
        session.setAttribute("transaction_status", transactionResponse.getTransactionStatus().toString());
        return UI.successUI(transactionResponse);
    }
}
