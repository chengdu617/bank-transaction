package com.hsbc.banktransaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsbc.banktransaction.base.response.Result;
import com.hsbc.banktransaction.domain.po.Transaction;
import com.hsbc.banktransaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(90001L);
        transaction.setType(11);
        transaction.setCstAccno("123456");
        transaction.setCounterAccno("654321");
        transaction.setAmount(BigDecimal.valueOf(1000.11));
        transaction.setDesc("转账");

        Mockito.when(transactionService.createTransaction(Mockito.any(Transaction.class))).thenReturn(transaction);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Result<Transaction> response = objectMapper.readValue(responseJson, Result.class);
        assertEquals(200, response.getCode());
    }

    @Test
    public void testCreateTransactionWithException() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(90001L);
        transaction.setType(11);
        transaction.setCstAccno("123456");
        transaction.setCounterAccno("654321");
        transaction.setAmount(BigDecimal.valueOf(1000.11));
        transaction.setDesc("转账");

        Mockito.when(transactionService.createTransaction(Mockito.any(Transaction.class))).thenThrow(new RuntimeException("创建交易失败"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Result<Transaction> response = objectMapper.readValue(responseJson, Result.class);
        assertEquals(500, response.getCode());
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        Long id = 1L;
        Mockito.doNothing().when(transactionService).deleteTransaction(id);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/transactions/{id}", id))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Result<Void> response = objectMapper.readValue(responseJson, Result.class);
        assertEquals(200, response.getCode());
    }

    @Test
    public void testDeleteTransactionWithException() throws Exception {
        Long id = 1L;
        Mockito.doThrow(new RuntimeException("删除交易失败")).when(transactionService).deleteTransaction(id);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/transactions/{id}", id))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Result<Void> response = objectMapper.readValue(responseJson, Result.class);
        assertEquals(500, response.getCode());
    }

    @Test
    public void testUpdateTransaction() throws Exception {
        Long id = 1L;
        Transaction transaction = new Transaction();
        transaction.setTransactionId(90001L);
        transaction.setType(11);
        transaction.setCstAccno("123456");
        transaction.setCounterAccno("654321");
        transaction.setAmount(BigDecimal.valueOf(2000.11));
        transaction.setDesc("转账");

        Mockito.when(transactionService.updateTransaction(Mockito.eq(id), Mockito.any(Transaction.class))).thenReturn(transaction);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/transactions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Result<Transaction> response = objectMapper.readValue(responseJson, Result.class);
        assertEquals(200, response.getCode());
    }

    @Test
    public void testUpdateTransactionWithException() throws Exception {
        Long id = 1L;
        Transaction transaction = new Transaction();
        transaction.setTransactionId(90001L);
        transaction.setType(11);
        transaction.setCstAccno("123456");
        transaction.setCounterAccno("654321");
        transaction.setAmount(BigDecimal.valueOf(2000.11));
        transaction.setDesc("转账");

        Mockito.when(transactionService.updateTransaction(Mockito.eq(id), Mockito.any(Transaction.class))).thenThrow(new RuntimeException("更新交易失败"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/transactions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Result<Transaction> response = objectMapper.readValue(responseJson, Result.class);
        assertEquals(500, response.getCode());
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(90001L);
        transaction.setType(11);
        transaction.setCstAccno("123456");
        transaction.setCounterAccno("654321");
        transaction.setAmount(BigDecimal.valueOf(1000.11));
        transaction.setDesc("转账");

        transactions.add(transaction);

        Mockito.when(transactionService.getAllTransactions()).thenReturn(transactions);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/transactions"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Result<List<Transaction>> response = objectMapper.readValue(responseJson, Result.class);
        assertEquals(200, response.getCode());
    }

    @Test
    public void testGetAllTransactionsWithException() throws Exception {
        Mockito.when(transactionService.getAllTransactions()).thenThrow(new RuntimeException("获取交易列表失败"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/transactions"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Result<List<Transaction>> response = objectMapper.readValue(responseJson, Result.class);
        assertEquals(500, response.getCode());
    }

    @Test
    public void testGetTransactionsByPage() throws Exception {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(90001L);
        transaction.setType(11);
        transaction.setCstAccno("123456");
        transaction.setCounterAccno("654321");
        transaction.setAmount(BigDecimal.valueOf(1000.11));
        transaction.setDesc("转账");

        transactions.add(transaction);

        Page<Transaction> pageResult = new PageImpl<>(transactions, PageRequest.of(page, size), transactions.size());

        Mockito.when(transactionService.page(pageable)).thenReturn(pageResult);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/transactions/page")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Result<Page<Transaction>> response = objectMapper.readValue(responseJson, Result.class);
        assertEquals(200, response.getCode());
    }

    @Test
    public void testGetTransactionsByPageWithException() throws Exception {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Mockito.when(transactionService.page(pageable)).thenThrow(new RuntimeException("分页查询交易失败"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/transactions/page")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Result<Page<Transaction>> response = objectMapper.readValue(responseJson, Result.class);
        assertEquals(500, response.getCode());
    }

    @Test
    public void testGetTransactionById() throws Exception {
        Long id = 1L;
        Transaction transaction = new Transaction();
        transaction.setTransactionId(90001L);
        transaction.setType(11);
        transaction.setCstAccno("123456");
        transaction.setCounterAccno("654321");
        transaction.setAmount(BigDecimal.valueOf(2000.11));
        transaction.setDesc("转账");

        Mockito.when(transactionService.getTransactionById(Mockito.eq(id))).thenReturn(transaction);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/transactions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Result<Transaction> response = objectMapper.readValue(responseJson, Result.class);
        assertEquals(200, response.getCode());
    }
}