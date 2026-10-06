package com.gautam.bank.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.gautam.bank.dto.request.transaction.DepositRequest;
import com.gautam.bank.dto.request.transaction.TransferRequest;
import com.gautam.bank.dto.request.transaction.WithdrawRequest;
import com.gautam.bank.dto.response.transaction.TransactionResponse;
import com.gautam.bank.entity.account.Account;
import com.gautam.bank.entity.transaction.Transaction;
import com.gautam.bank.enums.AccountStatus;
import com.gautam.bank.enums.TransactionType;
import com.gautam.bank.exception.ResourceNotFoundException;
import com.gautam.bank.mapper.TransactionMapper;
import com.gautam.bank.repository.AccountRepository;
import com.gautam.bank.repository.TransactionRepository;
import com.gautam.bank.service.CodeSequenceService;
import com.gautam.bank.service.TransactionService;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    private final TransactionMapper transactionMapper;

    private final CodeSequenceService codeSequenceService;

    @Override
    public TransactionResponse deposit(DepositRequest request) {

        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException("Account is not active.");
        }

        BigDecimal previousBalance = account.getBalance();

        BigDecimal newBalance = previousBalance.add(request.getAmount());

        account.setBalance(newBalance);

        accountRepository.save(account);

        Transaction transaction = Transaction.builder()

                .transactionNumber(codeSequenceService.generateTransactionNumber())
                .account(account)
                .fromAccount(account)
                .toAccount(account)
                .transactionType(TransactionType.DEPOSIT)
                .amount(request.getAmount())
                .previousBalance(previousBalance)
                .currentBalance(newBalance)
                .remarks(request.getRemarks())
                .build();

        transaction = transactionRepository.save(transaction);

        return transactionMapper.toResponse(transaction);
    }

    @Override
    @Transactional
    public TransactionResponse withdraw(WithdrawRequest request) {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));
        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException("Account is not active");
        }

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        BigDecimal previousBalance = account.getBalance();
        BigDecimal currentBalance = previousBalance.subtract(request.getAmount());
        account.setBalance(currentBalance);
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .transactionNumber(codeSequenceService.generateTransactionNumber())
                .account(account)
                .fromAccount(account)
                .toAccount(account)
                .transactionType(TransactionType.WITHDRAW)
                .amount(request.getAmount())
                .previousBalance(previousBalance)
                .currentBalance(currentBalance)
                .remarks(request.getRemarks())
                .build();

        transaction = transactionRepository.save(transaction);

        return transactionMapper.toResponse(transaction);
    }

    @Override
    @Transactional
    public TransactionResponse transfer(TransferRequest request) {

        if (request.getFromAccountNumber().equals(request.getToAccountNumber())) {
            throw new IllegalArgumentException("Source and destination accounts cannot be the same.");
        }

        Account fromAccount = accountRepository
                .findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found."));

        Account toAccount = accountRepository
                .findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Destination account not found."));

        if (fromAccount.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException("Source account is not active.");
        }

        if (toAccount.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException("Destination account is not active.");
        }

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        BigDecimal previousFromBalance = fromAccount.getBalance();
        BigDecimal previousToBalance = toAccount.getBalance();

        BigDecimal currentFromBalance = previousFromBalance.subtract(request.getAmount());
        BigDecimal currentToBalance = previousToBalance.add(request.getAmount());

        fromAccount.setBalance(currentFromBalance);
        toAccount.setBalance(currentToBalance);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = Transaction.builder()
                .transactionNumber(codeSequenceService.generateTransactionNumber())
                .account(fromAccount)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .transactionType(TransactionType.TRANSFER)
                .amount(request.getAmount())
                .previousBalance(previousFromBalance)
                .currentBalance(currentFromBalance)
                .remarks(request.getRemarks())
                .build();

        transaction = transactionRepository.save(transaction);

        return transactionMapper.toResponse(transaction);
    }

}