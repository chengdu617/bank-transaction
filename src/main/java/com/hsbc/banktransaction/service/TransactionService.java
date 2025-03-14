package com.hsbc.banktransaction.service;

import com.hsbc.banktransaction.domain.po.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    void deleteTransaction(Long id);

    Transaction updateTransaction(Long id, Transaction transaction);

    List<Transaction> getAllTransactions();

    Transaction getTransactionById(Long id);

    Page<Transaction> page(Pageable pageable);
}
