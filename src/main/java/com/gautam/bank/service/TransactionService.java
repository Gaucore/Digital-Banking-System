package com.gautam.bank.service;

import com.gautam.bank.dto.request.transaction.DepositRequest;
import com.gautam.bank.dto.request.transaction.TransferRequest;
import com.gautam.bank.dto.request.transaction.WithdrawRequest;
import com.gautam.bank.dto.response.transaction.TransactionResponse;

public interface TransactionService {

    TransactionResponse deposit(DepositRequest request);

    TransactionResponse withdraw(WithdrawRequest request);

    TransactionResponse transfer(TransferRequest request);

}
