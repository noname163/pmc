package com.utopia.pmc.mappers;

import org.springframework.stereotype.Component;

import com.utopia.pmc.data.dto.response.transaction.TransactionResponse;
import com.utopia.pmc.data.entities.Transaction;

@Component
public class TransactionMapper {
    public TransactionResponse mapEntityToDto(Transaction transaction) {
        return TransactionResponse
                .builder()
                .paymentPlan(transaction.getPaymentPlan())
                .transactionPaymentDate(transaction.getTransactionPaymentDate())
                .transactioncreatedDate(transaction.getTransactioncreatedDate())
                .amount(transaction.getAmount())
                .bankCode(transaction.getBankCode())
                .transactionStatus(transaction.getTransactionStatus())
                .build();
    }
}
