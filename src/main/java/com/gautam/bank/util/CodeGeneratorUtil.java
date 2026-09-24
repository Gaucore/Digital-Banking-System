package com.gautam.bank.util;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

@Component
public class CodeGeneratorUtil {

    private final AtomicLong customerSequence = new AtomicLong(1);
    private final AtomicLong accountSequence = new AtomicLong(100000000001L);
    private final AtomicLong transactionSequence = new AtomicLong(1);

    public String generateCustomerCode() {
        return String.format("CUST%06d", customerSequence.getAndIncrement());
    }

    public String generateAccountNumber() {
        return String.valueOf(accountSequence.getAndIncrement());
    }

    public String generateTransactionNumber() {
        return String.format("TXN%08d", transactionSequence.getAndIncrement());
    }

}
