package com.gautam.bank.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.gautam.bank.dto.request.customer.CustomerRequest;
import com.gautam.bank.dto.request.enableBankingCustomer.EnableInternetBankingRequest;
import com.gautam.bank.dto.response.account.CustomerAccountResponse;
import com.gautam.bank.dto.response.customer.CustomerResponse;
import com.gautam.bank.dto.response.customerProfile.CustomerProfileResponse;
import com.gautam.bank.dto.response.enableBankingCustomer.InternetBankingResponse;
import com.gautam.bank.dto.response.transaction.CustomerTransactionResponse;
import com.gautam.bank.dto.response.transaction.TransactionFilterRequest;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse getCustomerById(Long id);

    Page<CustomerResponse> getAllCustomers(
            int page,
            int size,
            String sortBy,
            String direction);

    CustomerResponse updateCustomer(Long id, CustomerRequest request);

    void deleteCustomer(Long id);

    InternetBankingResponse enableInternetBanking(EnableInternetBankingRequest request);

    CustomerProfileResponse getMyProfile();

    List<CustomerAccountResponse> getMyAccounts();

    List<CustomerTransactionResponse> getMyTransactions(TransactionFilterRequest request);
    
}