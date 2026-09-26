// package com.gautam.bank.service;

// import java.util.List;

// import org.springframework.data.domain.Page;

// import com.gautam.bank.dto.request.customer.CustomerRequest;
// import com.gautam.bank.dto.response.customer.CustomerResponse;

// public interface CustomerService {

//     CustomerResponse createCustomer(CustomerRequest request);

//     CustomerResponse getCustomerById(Long id);

//     // List<CustomerResponse> getAllCustomers();

//     Page<CustomerResponse> getAllCustomers(
//             int page,
//             int size,
//             String sortBy,
//             String direction);

//     CustomerResponse updateCustomer(Long id, CustomerRequest request);

//     // void deleteCustomer(Long id);

// }

package com.gautam.bank.service;

import org.springframework.data.domain.Page;

import com.gautam.bank.dto.request.customer.CustomerRequest;
import com.gautam.bank.dto.response.customer.CustomerResponse;

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

}