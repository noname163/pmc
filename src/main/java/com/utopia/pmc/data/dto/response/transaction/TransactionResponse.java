package com.utopia.pmc.data.dto.response.transaction;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.utopia.pmc.data.constants.statuses.TransactionStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonSerialize
public class TransactionResponse implements Serializable {
    private LocalDateTime transactioncreatedDate;
    private LocalDate transactionPaymentDate;
    private TransactionStatus transactionStatus;
    private Long amount;
    private String paymentPlan;
    private String bankCode;
}
