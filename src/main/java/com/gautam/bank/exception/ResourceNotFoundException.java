package com.gautam.bank.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}

// Used when data doesn't exist.

// Example

// Customer not found
// Account not found
// User not found
// Transaction not found