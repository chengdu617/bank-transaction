package com.hsbc.banktransaction.service.impl;


import com.hsbc.banktransaction.base.exception.BizErrorCode;
import com.hsbc.banktransaction.base.exception.BizException;
import com.hsbc.banktransaction.dao.TransactionRepository;
import com.hsbc.banktransaction.domain.po.Transaction;
import com.hsbc.banktransaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    public Transaction createTransaction(Transaction transaction) {

        Transaction exist = transactionRepository.getTransactionByTransactionId(transaction.getTransactionId());
        if(exist != null) {
            throw new BizException("交易已存在，transactionId:" + transaction.getTransactionId(), BizErrorCode.TRANSACTION_DUPLICATE);
        }
        transaction.setCreator("web");
        transaction.setCreateTime(LocalDateTime.now());
        transaction.setUpdator("web");
        transaction.setUpdateTime(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        Optional<Transaction> exist = transactionRepository.findById(id);
        if(exist.isEmpty()) {
            throw new BizException("交易不存在，id:" + id, BizErrorCode.TRANSACTION_DELETE_NOT_EXIST);
        }
        transactionRepository.deleteById(id);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction updateTransaction(Long id, Transaction transaction) {
        Optional<Transaction> exist = transactionRepository.findById(transaction.getId());
        if(exist.isEmpty()) {
            throw new BizException("交易不存在，id:" + transaction.getId(), BizErrorCode.TRANSACTION_UPDATE_NOT_EXIST);
        }
        Transaction existTransaction = exist.get();
        existTransaction.setCstAccno(transaction.getCstAccno());
        existTransaction.setCounterAccno(transaction.getCounterAccno());
        existTransaction.setAmount(transaction.getAmount());
        existTransaction.setDesc(transaction.getDesc());
        existTransaction.setUpdator("web");
        existTransaction.setUpdateTime(LocalDateTime.now());

        return transactionRepository.save(existTransaction);
    }

    public Page<Transaction> page(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }


}