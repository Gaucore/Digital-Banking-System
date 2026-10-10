
package com.gautam.bank.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gautam.bank.dto.request.customer.CustomerRequest;
import com.gautam.bank.dto.request.enableBankingCustomer.EnableInternetBankingRequest;
import com.gautam.bank.dto.response.account.CustomerAccountResponse;
import com.gautam.bank.dto.response.customer.CustomerResponse;
import com.gautam.bank.dto.response.customerProfile.CustomerProfileResponse;
import com.gautam.bank.dto.response.enableBankingCustomer.InternetBankingResponse;
import com.gautam.bank.dto.response.transaction.CustomerTransactionResponse;
import com.gautam.bank.dto.response.transaction.TransactionFilterRequest;
import com.gautam.bank.entity.account.Account;
import com.gautam.bank.entity.auth.User;
import com.gautam.bank.entity.customer.Customer;
import com.gautam.bank.entity.transaction.Transaction;
import com.gautam.bank.enums.CustomerStatus;
import com.gautam.bank.enums.UserRole;
import com.gautam.bank.exception.DuplicateResourceException;
import com.gautam.bank.exception.InvalidRequestException;
import com.gautam.bank.exception.ResourceNotFoundException;
import com.gautam.bank.mapper.CustomerMapper;
import com.gautam.bank.repository.AccountRepository;
import com.gautam.bank.repository.CustomerRepository;
import com.gautam.bank.repository.TransactionRepository;
import com.gautam.bank.repository.UserRepository;
import com.gautam.bank.service.CodeSequenceService;
import com.gautam.bank.service.CustomerService;
import com.gautam.bank.specification.TransactionSpecification;

import java.util.*;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

        private final CustomerRepository customerRepository;
        private final CustomerMapper customerMapper;
        private final CodeSequenceService codeSequenceService;
        private final AccountRepository accountRepository;
        private final TransactionRepository transactionRepository;

        private UserRepository userRepository;
        private PasswordEncoder passwordEncoder;

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

        @Override
        @Transactional
        public InternetBankingResponse enableInternetBanking(
                        EnableInternetBankingRequest request) {

                // Step 1 - Find Customer
                Customer customer = customerRepository.findById(request.getCustomerId())
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

                // Step 2 - Check Internet Banking Already Enabled
                if (customer.getUser() != null) {
                        throw new IllegalArgumentException(
                                        "Internet Banking is already enabled.");
                }

                // Step 3 - Check Username Already Exists
                if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                        throw new DuplicateResourceException(
                                        "Username already exists.");
                }

                // Step 4 - Encode Password
                String encodedPassword = passwordEncoder.encode(request.getPassword());

                // Step 5 - Create User
                User user = User.builder()
                                .username(request.getUsername())
                                .password(encodedPassword)
                                .role(UserRole.CUSTOMER)
                                .build();

                // Step 6 - Save User
                user = userRepository.save(user);

                // Step 7 - Link User With Customer
                customer.setUser(user);

                // Step 8 - Save Customer
                customer = customerRepository.save(customer);

                // Step 9 - Return Response
                return InternetBankingResponse.builder()
                                .customerCode(customer.getCustomerCode())
                                .username(user.getUsername())
                                .role(user.getRole().name())
                                .message("Internet Banking enabled successfully.")
                                .build();
        }

        @Override
        public CustomerProfileResponse getMyProfile() {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("customer not found."));

                return CustomerProfileResponse.builder().customerCode(customer.getCustomerCode())
                                .firstName(customer.getFirstName()).lastName(customer.getLastName())
                                .email(customer.getEmail())
                                .phone(customer.getPhone()).status(customer.getStatus().name())
                                .username(user.getUsername()).build();
        }

        @Override
        @Transactional(readOnly = true)
        public List<CustomerAccountResponse> getMyAccounts() {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("user not found."));

                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("customer not found."));

                List<Account> accounts = accountRepository.findByCustomer(customer);

                return accounts.stream()
                                .map(account -> CustomerAccountResponse.builder()
                                                .accountNumber(account.getAccountNumber())
                                                .accountType(account.getAccountType())
                                                .balance(account.getBalance())
                                                .accountStatus(account.getAccountStatus())
                                                .build())
                                .toList();
        }

        // @Override
        // @Transactional(readOnly = true)
        // public List<CustomerTransactionResponse> getMyTransactions() {
        // String username =
        // SecurityContextHolder.getContext().getAuthentication().getName();
        // User user = userRepository.findByUsername(username)
        // .orElseThrow(() -> new ResourceNotFoundException("user not found."));
        // Customer customer = customerRepository.findByUser(user)
        // .orElseThrow(() -> new ResourceNotFoundException("customer not found."));
        // List<Account> accounts = accountRepository.findByCustomer(customer);
        // List<CustomerTransactionResponse> responses = new ArrayList<>();
        // for (Account account : accounts) {
        // List<Transaction> transactions =
        // transactionRepository.findByAccountOrderByCreatedAtDesc(account);
        // for (Transaction transaction : transactions) {
        // responses.add(
        // CustomerTransactionResponse.builder()
        // .transactionNumber(transaction.getTransactionNumber())
        // .transactionType(transaction.getTransactionType())
        // .fromAccountNumber(
        // transaction.getFromAccount() != null
        // ? transaction.getFromAccount().getAccountNumber()
        // : null)
        // .toAccountNumber(
        // transaction.getToAccount() != null
        // ? transaction.getToAccount().getAccountNumber()
        // : null)
        // .amount(transaction.getAmount())
        // .previousBalance(transaction.getPreviousBalance())
        // .currentBalance(transaction.getCurrentBalance())
        // .remarks(transaction.getRemarks())
        // .transactionDate(transaction.getCreatedAt().toLocalDate())
        // .build());
        // }
        // }

        // return responses;
        // }

        @Override
        @Transactional(readOnly = true)
        public List<CustomerTransactionResponse> getMyTransactions(
                        TransactionFilterRequest request) {

                // ==========================================================
                // STEP 1 : Get Logged In User
                // ==========================================================
                String username = SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getName();

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

                if (request.getAccountNumber() != null
                                && !request.getAccountNumber().isBlank()) {

                        Account account = accountRepository
                                        .findByAccountNumber(request.getAccountNumber())
                                        .orElseThrow(() -> new ResourceNotFoundException("Account not found."));

                        if (!account.getCustomer().getId().equals(customer.getId())) {
                                throw new IllegalArgumentException(
                                                "This account does not belong to the logged-in customer.");
                        }
                }

                // ==========================================================
                // STEP 2 : Apply Dynamic Filters
                // ==========================================================
                Specification<Transaction> specification = TransactionSpecification.filter(request, customer);

                // ==========================================================
                // STEP 3 : Fetch Transactions
                // ==========================================================
                List<Transaction> transactions = transactionRepository.findAll(specification);

                List<CustomerTransactionResponse> responses = new ArrayList<>();

                // ==========================================================
                // STEP 4 : Only Return Logged-In Customer Transactions
                // ==========================================================
                // for (Transaction transaction : transactions) {

                // Account account = transaction.getAccount();

                // if (account == null
                // || account.getCustomer() == null
                // || !account.getCustomer().getId().equals(customer.getId())) {
                // continue;
                // }

                // responses.add(
                // CustomerTransactionResponse.builder()
                // .transactionNumber(transaction.getTransactionNumber())
                // .transactionType(transaction.getTransactionType())
                // .fromAccountNumber(
                // transaction.getFromAccount() != null
                // ? transaction.getFromAccount()
                // .getAccountNumber()
                // : null)
                // .toAccountNumber(
                // transaction.getToAccount() != null
                // ? transaction.getToAccount()
                // .getAccountNumber()
                // : null)
                // .amount(transaction.getAmount())
                // .previousBalance(transaction.getPreviousBalance())
                // .currentBalance(transaction.getCurrentBalance())
                // .remarks(transaction.getRemarks())
                // .transactionDate(
                // transaction.getCreatedAt().toLocalDate())
                // .build());
                // }

                for (Transaction transaction : transactions) {

                        responses.add(
                                        CustomerTransactionResponse.builder()
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
                                                        .transactionDate(
                                                                        transaction.getCreatedAt().toLocalDate())
                                                        .build());
                }

                return responses;
        }

}
