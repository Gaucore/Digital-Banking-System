
package com.gautam.bank.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gautam.bank.dto.request.customer.CustomerRequest;
import com.gautam.bank.dto.response.customer.CustomerResponse;
import com.gautam.bank.entity.customer.Customer;
import com.gautam.bank.enums.CustomerStatus;
import com.gautam.bank.exception.DuplicateResourceException;
import com.gautam.bank.exception.InvalidRequestException;
import com.gautam.bank.exception.ResourceNotFoundException;
import com.gautam.bank.mapper.CustomerMapper;
import com.gautam.bank.repository.CustomerRepository;
import com.gautam.bank.service.CodeSequenceService;
import com.gautam.bank.service.CustomerService;

import java.util.*;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CodeSequenceService codeSequenceService;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone number already exists");
        }

        Customer customer = customerMapper.toEntity(request);
        customer.setStatus(CustomerStatus.ACTIVE);

        customer.setCustomerCode(codeSequenceService.generateCustomerCode());

        // old Method
        // customer = customerRepository.save(customer);
        // customer.setCustomerCode(String.format("CUST%06d", customer.getId()));
        customer = customerRepository.save(customer);

        return customerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id : " + id));

        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getAllCustomers(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Customer> customerPage = customerRepository.findAll(pageable);

        return customerPage.map(customerMapper::toResponse);
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id : " + id));

        if (customerRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (customerRepository.existsByPhoneAndIdNot(request.getPhone(), id)) {
            throw new DuplicateResourceException("Phone already exists");
        }

        customerMapper.updateEntity(request, customer);
        customer = customerRepository.save(customer);
        return customerMapper.toResponse(customer);
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id : " + id));

        if (customer.getStatus() == CustomerStatus.INACTIVE) {
            throw new InvalidRequestException(
                    "Customer is already inactive.");

        }

        customer.setStatus(CustomerStatus.INACTIVE);

        customerRepository.save(customer);

    }
}
