package com.hsbc.banktransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BankTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankTransactionApplication.class, args);
    }

}
