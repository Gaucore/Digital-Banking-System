package com.gautam.bank.exception;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}

// it checks the balance it is avaiable that much amount or not if amount
// doesn't avaiable the amount it gives error"

// insufficent Balance