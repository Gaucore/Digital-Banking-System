package com.gautam.bank.service.impl;

import com.gautam.bank.repository.TransactionRepository;
import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.control.MappingControl.Use;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gautam.bank.dto.request.beneficiary.BeneficiaryRequest;
import com.gautam.bank.dto.request.beneficiary.BeneficiaryTransferRequest;
import com.gautam.bank.dto.request.beneficiary.UpdateBeneficiaryRequest;
import com.gautam.bank.dto.request.statement.StatementRequest;
import com.gautam.bank.dto.response.beneficiary.BeneficiaryResponse;
import com.gautam.bank.dto.response.common.ApiResponse;
import com.gautam.bank.dto.response.statement.StatementResponse;
import com.gautam.bank.dto.response.transaction.TransactionResponse;
import com.gautam.bank.entity.account.Account;
import com.gautam.bank.entity.auth.User;
import com.gautam.bank.entity.beneficiary.Beneficiary;
import com.gautam.bank.entity.customer.Customer;
import com.gautam.bank.entity.transaction.Transaction;
import com.gautam.bank.enums.AccountStatus;
import com.gautam.bank.enums.TransactionType;
import com.gautam.bank.exception.ResourceNotFoundException;
import com.gautam.bank.repository.AccountRepository;
import com.gautam.bank.repository.BeneficiaryRepository;
import com.gautam.bank.repository.CustomerRepository;
import com.gautam.bank.repository.UserRepository;
import com.gautam.bank.service.BeneficiaryService;
import com.gautam.bank.service.CodeSequenceService;

// import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BeneficiaryServiceImpl implements BeneficiaryService {

        private final TransactionRepository transactionRepository;
        private final BeneficiaryRepository beneficiaryRepository;
        private final CustomerRepository customerRepository;
        private final AccountRepository accountRepository;
        private final UserRepository userRepository;

        private final CodeSequenceService codeSequenceService;

        // BeneficiaryServiceImpl(TransactionRepository transactionRepository) {
        // this.transactionRepository = transactionRepository;
        // }

        @Override
        @Transactional
        public BeneficiaryResponse addBeneficiary(BeneficiaryRequest request) {

                String username = SecurityContextHolder.getContext().getAuthentication().getName();

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

                Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));

                if (account.getCustomer().getId().equals(customer.getId())) {
                        throw new IllegalArgumentException("You cannot add your own account as beneficiary.");
                }

                if (account.getAccountStatus() != AccountStatus.ACTIVE) {
                        throw new IllegalArgumentException("Beneficiary account is not active.");
                }

                beneficiaryRepository.findByCustomerAndAccount(customer, account).ifPresent(beneficiary -> {
                        throw new IllegalArgumentException("Beneficiary already exists.");
                });

                Beneficiary beneficiary = Beneficiary.builder()
                                .beneficiaryCode(codeSequenceService.generateBeneficiaryCode())
                                .nickName(request.getNickName())
                                .customer(customer)
                                .account(account)
                                .build();

                beneficiary = beneficiaryRepository.save(beneficiary);

                return BeneficiaryResponse.builder()
                                .beneficiaryCode(beneficiary.getBeneficiaryCode())
                                .nickname(beneficiary.getNickName())
                                .accountNumber(account.getAccountNumber())
                                .accountHolderName(account.getCustomer().getFirstName() + " "
                                                + account.getCustomer().getLastName())
                                .accountType(account.getAccountType())
                                .createdAt(beneficiary.getCreatedAt().toLocalDate())
                                .build();
        }

        @Override
        @Transactional(readOnly = true)
        public List<BeneficiaryResponse> getMyBeneficiaries() {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));
                List<Beneficiary> beneficiaries = beneficiaryRepository.findByCustomerOrderByCreatedAtDesc(customer);

                return beneficiaries.stream()
                                .map(beneficiary -> BeneficiaryResponse.builder()
                                                .beneficiaryCode(beneficiary.getBeneficiaryCode())
                                                .nickname(beneficiary.getNickName())
                                                .accountNumber(beneficiary.getAccount().getAccountNumber())
                                                .accountHolderName(beneficiary.getAccount()
                                                                .getCustomer()
                                                                .getFirstName()
                                                                + " "
                                                                + beneficiary.getAccount()
                                                                                .getCustomer()
                                                                                .getLastName())
                                                .accountType(beneficiary.getAccount().getAccountType())
                                                .createdAt(beneficiary.getCreatedAt().toLocalDate())
                                                .build())
                                .toList();
        }

        @Override
        @Transactional
        public BeneficiaryResponse updateBeneficiary(String beneficiaryCode, UpdateBeneficiaryRequest request) {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));
                Beneficiary beneficiary = beneficiaryRepository.findByBeneficiaryCode(beneficiaryCode)
                                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found."));
                if (!beneficiary.getCustomer().getId().equals(customer.getId())) {
                        throw new IllegalArgumentException("You are not authorized to update this beneficiary.");
                }
                beneficiary.setNickName(request.getNickName());
                beneficiary = beneficiaryRepository.save(beneficiary);
                return BeneficiaryResponse.builder()
                                .beneficiaryCode(beneficiary.getBeneficiaryCode())
                                .nickname(beneficiary.getNickName())
                                .accountNumber(beneficiary.getAccount().getAccountNumber())
                                .accountHolderName(beneficiary.getAccount().getCustomer().getFirstName() + " "
                                                + beneficiary.getAccount().getCustomer().getLastName())
                                .accountType(beneficiary.getAccount().getAccountType())
                                .createdAt(beneficiary.getCreatedAt().toLocalDate())
                                .build();
        }

        @Override
        @Transactional
        public ApiResponse deleteBeneficiary(String beneficiaryCode) {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));
                Beneficiary beneficiary = beneficiaryRepository.findByBeneficiaryCode(beneficiaryCode)
                                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found."));
                if (!beneficiary.getCustomer().getId().equals(customer.getId())) {
                        throw new IllegalArgumentException("You are not authorized to delete this beneficiary.");
                }
                beneficiaryRepository.delete(beneficiary);
                return ApiResponse.builder()
                                .success(true)
                                .message("Beneficiary deleted successfully.")
                                .build();
        }

        @Override
        @Transactional
        public TransactionResponse transferToBeneficiary(BeneficiaryTransferRequest request) {

                String username = SecurityContextHolder.getContext().getAuthentication().getName();

                System.out.println("Logged In User : " + username);

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

                System.out.println("Logged In Customer Id : " + customer.getId());
                System.out.println("Logged In Customer Code : " + customer.getCustomerCode());

                Beneficiary beneficiary = beneficiaryRepository
                                .findByBeneficiaryCode(request.getBeneficiaryCode())
                                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found."));

                System.out.println("Beneficiary Code : " + beneficiary.getBeneficiaryCode());

                System.out.println("Beneficiary Owner Id : "
                                + beneficiary.getCustomer().getId());

                System.out.println("Beneficiary Account : "
                                + beneficiary.getAccount().getAccountNumber());

                if (!beneficiary.getCustomer().getId().equals(customer.getId())) {
                        throw new IllegalArgumentException("You are not authorized to use this beneficiary.");
                }

                Account fromAccount = accountRepository
                                .findByAccountNumber(request.getFromAccountNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Source account not found."));

                if (!fromAccount.getCustomer().getId().equals(customer.getId())) {
                        throw new IllegalArgumentException("Source account does not belong to you.");
                }

                Account toAccount = beneficiary.getAccount();

                if (fromAccount.getAccountStatus() != AccountStatus.ACTIVE) {
                        throw new IllegalArgumentException("Source account is not active.");
                }

                if (toAccount.getAccountStatus() != AccountStatus.ACTIVE) {
                        throw new IllegalArgumentException("Beneficiary account is not active.");
                }

                if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
                        throw new IllegalArgumentException("Insufficient balance.");
                }

                BigDecimal fromPreviousBalance = fromAccount.getBalance();
                BigDecimal toPreviousBalance = toAccount.getBalance();

                fromAccount.setBalance(fromPreviousBalance.subtract(request.getAmount()));

                toAccount.setBalance(toPreviousBalance.add(request.getAmount()));

                accountRepository.save(fromAccount);
                accountRepository.save(toAccount);

                Transaction debitTransaction = Transaction.builder()
                                .transactionNumber(codeSequenceService.generateTransactionNumber())
                                .transactionType(TransactionType.TRANSFER)
                                .account(fromAccount)
                                .fromAccount(fromAccount)
                                .toAccount(toAccount)
                                .amount(request.getAmount())
                                .previousBalance(fromPreviousBalance)
                                .currentBalance(fromAccount.getBalance())
                                .remarks(request.getRemarks())
                                .build();

                debitTransaction = transactionRepository.save(debitTransaction);

                Transaction creditTransaction = Transaction.builder()
                                .transactionNumber(codeSequenceService.generateTransactionNumber())
                                .transactionType(TransactionType.TRANSFER)
                                .account(toAccount)
                                .fromAccount(fromAccount)
                                .toAccount(toAccount)
                                .amount(request.getAmount())
                                .previousBalance(toPreviousBalance)
                                .currentBalance(toAccount.getBalance())
                                .remarks(request.getRemarks())
                                .build();

                transactionRepository.save(creditTransaction);

                return TransactionResponse.builder()
                                .transactionNumber(debitTransaction.getTransactionNumber())
                                .transactionType(debitTransaction.getTransactionType())
                                .fromAccountNumber(fromAccount.getAccountNumber())
                                .toAccountNumber(toAccount.getAccountNumber())
                                .amount(debitTransaction.getAmount())
                                .previousBalance(debitTransaction.getPreviousBalance())
                                .currentBalance(debitTransaction.getCurrentBalance())
                                .remarks(debitTransaction.getRemarks())
                                .createdAt(debitTransaction.getCreatedAt().toLocalDate())
                                .build();
        }

        
}
