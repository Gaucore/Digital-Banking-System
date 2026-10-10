package com.gautam.bank.service.impl;

import com.gautam.bank.util.excel.StatementExcelGenerator;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gautam.bank.dto.request.statement.MonthlyStatementRequest;
import com.gautam.bank.dto.request.statement.StatementRequest;
import com.gautam.bank.dto.response.statement.StatementResponse;
import com.gautam.bank.entity.account.Account;
import com.gautam.bank.entity.auth.User;
import com.gautam.bank.entity.customer.Customer;
import com.gautam.bank.entity.transaction.Transaction;
import com.gautam.bank.exception.ResourceNotFoundException;
import com.gautam.bank.repository.AccountRepository;
import com.gautam.bank.repository.CustomerRepository;
import com.gautam.bank.repository.TransactionRepository;
import com.gautam.bank.repository.UserRepository;
import com.gautam.bank.service.StatementService;
import com.gautam.bank.util.pdf.StatementPdfGenerator;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {

        private final StatementExcelGenerator statementExcelGenerator;
        private final UserRepository userRepository;
        private final CustomerRepository customerRepository;
        private final AccountRepository accountRepository;
        private final TransactionRepository transactionRepository;
        private final StatementPdfGenerator statementPdfGenerator;

        @Override
        @Transactional(readOnly = true)
        public List<StatementResponse> getMiniStatement(String accountNumber) {

                String username = SecurityContextHolder.getContext().getAuthentication().getName();

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

                Account account = accountRepository.findByAccountNumber(accountNumber)
                                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));

                if (!account.getCustomer().getId().equals(customer.getId())) {
                        throw new IllegalArgumentException("You are not authorized to view this account.");
                }

                List<Transaction> transactions = transactionRepository.findTop10ByAccountOrderByCreatedAtDesc(account);

                return transactions.stream()
                                .map(transaction -> StatementResponse.builder()
                                                .transactionNumber(transaction.getTransactionNumber())
                                                .transactionType(transaction.getTransactionType())
                                                .fromAccountNumber(transaction.getFromAccount() != null
                                                                ? transaction.getFromAccount().getAccountNumber()
                                                                : null)
                                                .toAccountNumber(transaction.getToAccount() != null
                                                                ? transaction.getToAccount().getAccountNumber()
                                                                : null)
                                                .amount(transaction.getAmount())
                                                .previousBalance(transaction.getPreviousBalance())
                                                .currentBalance(transaction.getCurrentBalance())
                                                .remarks(transaction.getRemarks())
                                                .transactionDate(transaction.getCreatedAt())
                                                .build())
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public List<StatementResponse> getStatement(StatementRequest request) {

                String username = SecurityContextHolder.getContext().getAuthentication().getName();

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

                Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));
                if (!account.getCustomer().getId().equals(customer.getId())) {
                        throw new IllegalArgumentException("You are not authorized.");
                }

                LocalDateTime fromDate = request.getFromDate().atStartOfDay();
                LocalDateTime toDate = request.getToDate().atTime(23, 59, 59);

                List<Transaction> transactions = transactionRepository
                                .findByAccountAndCreatedAtBetweenOrderByCreatedAtDesc(account, fromDate, toDate);

                return transactions.stream()
                                .map(transaction -> StatementResponse.builder()
                                                .transactionNumber(transaction.getTransactionNumber())
                                                .transactionType(transaction.getTransactionType())
                                                .fromAccountNumber(
                                                                transaction.getFromAccount() != null
                                                                                ? transaction.getFromAccount()
                                                                                                .getAccountNumber()
                                                                                : null)
                                                .toAccountNumber(
                                                                transaction.getToAccount() != null
                                                                                ? transaction.getToAccount()
                                                                                                .getAccountNumber()
                                                                                : null)
                                                .amount(transaction.getAmount())
                                                .previousBalance(transaction.getPreviousBalance())
                                                .currentBalance(transaction.getCurrentBalance())
                                                .remarks(transaction.getRemarks())
                                                .transactionDate(transaction.getCreatedAt())
                                                .build())
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public List<StatementResponse> getMonthlyStatement(MonthlyStatementRequest request) {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

                Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));

                if (!account.getCustomer().getId().equals(customer.getId())) {
                        throw new IllegalArgumentException("You are not authorized");
                }

                YearMonth yearMonth = YearMonth.of(request.getYear(), request.getMonth());
                LocalDateTime fromDate = yearMonth.atDay(1).atStartOfDay();
                LocalDateTime toDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);

                List<Transaction> transactions = transactionRepository
                                .findByAccountAndCreatedAtBetweenOrderByCreatedAtDesc(account, fromDate, toDate);

                return transactions.stream()
                                .map(transaction -> StatementResponse.builder()
                                                .transactionNumber(transaction.getTransactionNumber())
                                                .transactionType(transaction.getTransactionType())
                                                .fromAccountNumber(transaction.getFromAccount() != null
                                                                ? transaction.getFromAccount().getAccountNumber()
                                                                : null)
                                                .toAccountNumber(transaction.getToAccount() != null
                                                                ? transaction.getToAccount().getAccountNumber()
                                                                : null)
                                                .amount(transaction.getAmount())
                                                .previousBalance(transaction.getPreviousBalance())
                                                .currentBalance(transaction.getCurrentBalance())
                                                .remarks(transaction.getRemarks())
                                                .transactionDate(transaction.getCreatedAt())
                                                .build())
                                .toList();

        }

        @Override
        @Transactional(readOnly = true)
        public byte[] downloadStatementPdf(StatementRequest request) {

                List<StatementResponse> statements = getStatement(request);

                Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));

                String customerName = account.getCustomer().getFirstName() + " " + account.getCustomer().getLastName();

                String period = request.getFromDate() + " to " + request.getToDate();

                // return statementPdfGenerator.generatePdf(customerName,
                // account.getAccountNumber(), period, statements);
                byte[] pdf = statementPdfGenerator.generatePdf(
                                customerName,
                                account.getAccountNumber(),
                                period,
                                statements);

                System.out.println("PDF Size : " + pdf.length);

                return pdf;
        }

        @Override
        @Transactional(readOnly = true)
        public byte[] downloadStatementExcel(StatementRequest request) {

                List<StatementResponse> statements = getStatement(request);

                Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));

                String customerName = account.getCustomer().getFirstName() + " " + account.getCustomer().getLastName();

                String period = request.getFromDate() + " to " + request.getToDate();

                return statementExcelGenerator.generateExcel(
                                customerName,
                                account.getAccountNumber(),
                                period,
                                statements);
        }

}
