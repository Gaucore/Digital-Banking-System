package com.gautam.bank.service;

import java.util.List;

import com.gautam.bank.dto.request.customer.CustomerRequest;
import com.gautam.bank.dto.response.customer.CustomerResponse;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse getCustomerById(Long id);

    // List<CustomerResponse> getAllCustomer();

    // CustomerResponse updateCustomer(Long id, CustomerRequest request);

    // void deleteCustomer(Long id);

}
