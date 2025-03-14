package com.hsbc.banktransaction.dao;

import com.hsbc.banktransaction.domain.po.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction getTransactionByTransactionId(Long transactionId);
}
