package com.hsbc.banktransaction.service;

import com.hsbc.banktransaction.base.exception.BizException;
import com.hsbc.banktransaction.dao.TransactionRepository;
import com.hsbc.banktransaction.domain.po.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    private Transaction sampleTransaction;

    @BeforeEach
    public void setUp() {
        sampleTransaction = new Transaction();
        sampleTransaction.setTransactionId(90001L);
        sampleTransaction.setType(11);
        sampleTransaction.setCstAccno("123456");
        sampleTransaction.setCounterAccno("654321");
        sampleTransaction.setAmount(BigDecimal.valueOf(1000.11));
        sampleTransaction.setDesc("转账");

        transactionService.createTransaction(sampleTransaction);
    }

    @Test
    public void testCreateTransaction() {
        Mockito.when(transactionRepository.getTransactionByTransactionId(sampleTransaction.getTransactionId())).thenReturn(null);
        Mockito.when(transactionRepository.save(sampleTransaction)).thenReturn(sampleTransaction);

        Transaction result = transactionService.createTransaction(sampleTransaction);
        assertEquals(sampleTransaction, result);
    }

    @Test
    public void testCreateTransactionWithExistingCode() {
        Mockito.when(transactionRepository.getTransactionByTransactionId(sampleTransaction.getTransactionId())).thenReturn(sampleTransaction);

        BizException exception = assertThrows(BizException.class, () -> {
            transactionService.createTransaction(sampleTransaction);
        });
        assertEquals("创建失败，交易已存在", exception.getErrorCode().getMessage());
    }

    @Test
    public void testDeleteTransaction() {
        Long id = 1L;
        Mockito.when(transactionRepository.findById(id)).thenReturn(Optional.of(sampleTransaction));
        Mockito.doNothing().when(transactionRepository).deleteById(id);

        transactionService.deleteTransaction(id);
        Mockito.verify(transactionRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void testDeleteNonExistingTransaction() {
        Long id = 1L;
        Mockito.when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        BizException exception = assertThrows(BizException.class, () -> {
            transactionService.deleteTransaction(id);
        });
        assertEquals("删除失败，交易不存在", exception.getErrorCode().getMessage());
    }

    @Test
    public void testUpdateTransaction() {
        Long id = 1L;
        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setTransactionId(90001L);
        updatedTransaction.setType(11);
        updatedTransaction.setCstAccno("123456");
        updatedTransaction.setCounterAccno("654321");
        updatedTransaction.setAmount(BigDecimal.valueOf(2000.11));
        updatedTransaction.setDesc("转账");

        Mockito.when(transactionRepository.findById(id)).thenReturn(Optional.of(sampleTransaction));
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(updatedTransaction);

        Transaction result = transactionService.updateTransaction(id, updatedTransaction);
        assertEquals(updatedTransaction, result);
    }

    @Test
    public void testUpdateNonExistingTransaction() {
        Long id = 1L;
        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setTransactionId(90001L);
        updatedTransaction.setType(11);
        updatedTransaction.setCstAccno("123456");
        updatedTransaction.setCounterAccno("654321");
        updatedTransaction.setAmount(BigDecimal.valueOf(2000.11));
        updatedTransaction.setDesc("转账");

        Mockito.when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        BizException exception = assertThrows(BizException.class, () -> {
            transactionService.updateTransaction(id, updatedTransaction);
        });
        assertEquals("更新失败，交易不存在", exception.getErrorCode().getMessage());
    }

    @Test
    public void testGetAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(sampleTransaction);

        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllTransactions();
        assertEquals(transactions, result);
    }

    @Test
    public void testGetTransactionsByPage() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(sampleTransaction);
        Page<Transaction> pageResult = new PageImpl<>(transactions, pageRequest, transactions.size());

        Mockito.when(transactionRepository.findAll(pageRequest)).thenReturn(pageResult);

        Page<Transaction> result = transactionService.page(pageRequest);
        assertEquals(pageResult, result);
    }

    @Test
    public void testGetPage() {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("id").descending());
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setTransactionId(90001L);
        transaction.setType(11);
        transaction.setCstAccno("123456");
        transaction.setCounterAccno("654321");
        transaction.setAmount(BigDecimal.valueOf(2000.11));
        transaction.setDesc("转账");

        Page<Transaction> page = new PageImpl<>(Collections.singletonList(transaction));
        when(transactionRepository.findAll(pageable)).thenReturn(page);

        Page<Transaction> result = transactionService.page(pageable);
        assertEquals(page.getTotalElements(), result.getTotalElements());
        verify(transactionRepository).findAll(pageable);
    }

    @Test
    public void testGetTransactionById() {
        Long id = 1L;

        Mockito.when(transactionRepository.findById(id)).thenReturn(Optional.of(sampleTransaction));
        Transaction result = transactionService.getTransactionById(id);
        assertEquals(sampleTransaction, result);
    }

    @Test
    public void testCreateTransactionWithNull() {
        assertThrows(NullPointerException.class, () -> {
            transactionService.createTransaction(null);
        });
    }

    @Test
    public void testUpdateTransactionWithNull() {
        Long id = 1L;
        assertThrows(BizException.class, () -> {
            transactionService.updateTransaction(id, null);
        });
    }

    @Test
    public void testDeleteTransactionWithNullId() {
        assertThrows(BizException.class, () -> {
            transactionService.deleteTransaction(null);
        });
    }

    @Test
    public void testGetAllTransactionsWhenRepositoryReturnsNull() {
        when(transactionRepository.findAll()).thenReturn(null);
        List<Transaction> result = transactionService.getAllTransactions();
        assertEquals(null, result);
    }
}