package com.gautam.bank.service;

import java.util.List;

import com.gautam.bank.dto.request.account.AccountRequest;
import com.gautam.bank.dto.response.account.AccountResponse;

public interface AccountService {

    AccountResponse openAccount(AccountRequest request);

    AccountResponse getAccountByNumber(String accountNumber);

    List<AccountResponse> getCustomerAccounts(Long customerId);

}
