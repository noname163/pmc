package com.utopia.pmc.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utopia.pmc.data.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public Optional<Transaction> findByTransactionId(String transactionId);
}
