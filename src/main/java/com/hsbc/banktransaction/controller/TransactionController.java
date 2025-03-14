package com.hsbc.banktransaction.controller;


import com.hsbc.banktransaction.base.response.Result;
import com.hsbc.banktransaction.domain.po.Transaction;
import com.hsbc.banktransaction.service.TransactionService;
import com.hsbc.banktransaction.service.param.TransactionParam;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public Result<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return Result.success(transactions);
    }

    @GetMapping("/{id}")
    public Result<Transaction> get(@PathVariable(value = "id") Long id) {
        return Result.success(transactionService.getTransactionById(id));
    }

    @PostMapping
    public Result<Transaction> createTransaction(@Valid @RequestBody TransactionParam param) {
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(param, transaction);
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return Result.success(createdTransaction);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Transaction> updateTransaction(@PathVariable Long id, @Valid @RequestBody Transaction param) {
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(param, transaction);
        Transaction updatedTransaction = transactionService.updateTransaction(id, transaction);
        return Result.success(updatedTransaction);
    }

    @GetMapping("/page")
    public Result<Page<Transaction>> getTransactionsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Transaction> transactionPage = transactionService.page(pageable);
        return Result.success(transactionPage);
    }
}
