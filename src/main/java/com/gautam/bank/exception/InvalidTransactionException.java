package com.gautam.bank.exception;

public class InvalidTransactionException extends RuntimeException {

    public InvalidTransactionException(String message) {
        super(message);
    }
}

// when user type invalid the data it gives invalid
