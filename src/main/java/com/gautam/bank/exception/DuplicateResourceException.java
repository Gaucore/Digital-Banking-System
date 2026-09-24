package com.gautam.bank.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

}

// it finds the duplicate value

// Email already exists
// Phone already exists
// Account already exists