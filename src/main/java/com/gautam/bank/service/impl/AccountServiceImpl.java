package com.gautam.bank.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.gautam.bank.dto.request.account.AccountRequest;
import com.gautam.bank.dto.response.account.AccountResponse;
import com.gautam.bank.entity.account.Account;
import com.gautam.bank.entity.customer.Customer;
import com.gautam.bank.enums.AccountStatus;
import com.gautam.bank.exception.ResourceNotFoundException;
import com.gautam.bank.mapper.AccountMapper;
import com.gautam.bank.repository.AccountRepository;
import com.gautam.bank.repository.CustomerRepository;
import com.gautam.bank.service.AccountService;
import com.gautam.bank.service.CodeSequenceService;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;
    private final CodeSequenceService codeSequenceService;

    @Override
    @Transactional
    public AccountResponse openAccount(AccountRequest request) {

        Customer customer = customerRepository.findByCustomerCode(
                request.getCustomerCode()).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Customer not found."));

        Account account = accountMapper.toEntity(request);
        account.setCustomer(customer);
        account.setBalance(BigDecimal.ZERO);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAccountNumber(codeSequenceService.generateAccountNumber());
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toResponse(savedAccount);
    }
}
