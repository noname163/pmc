package com.utopia.pmc.data.dto.response.transaction;

import java.time.LocalDate;

import com.utopia.pmc.data.constants.statuses.TransactionStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TransactionResponse {
    private LocalDate transactioncreatedDate;
    private LocalDate transactionPaymentDate;
    private TransactionStatus transactionStatus;
    private Long amount;
    private String paymentPlan;
    private String bankCode;
}
