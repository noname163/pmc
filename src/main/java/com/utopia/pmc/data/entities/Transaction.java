package com.utopia.pmc.data.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.utopia.pmc.data.constants.statuses.TransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Transaction_tbl")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @SequenceGenerator(name = "transaction_sequence", sequenceName = "transaction_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "transaction_sequence")
    private long id;

    private String transactionId;
    private LocalDateTime transactioncreatedDate;
    private LocalDate transactionPaymentDate;
    private TransactionStatus transactionStatus;
    private LocalDateTime expireDate;
    private Long amount;
    private String paymentPlan;
    private String bankCode;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
