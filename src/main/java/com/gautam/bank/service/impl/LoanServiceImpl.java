package com.gautam.bank.service.impl;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gautam.bank.dto.request.loan.ApplyLoanRequest;
import com.gautam.bank.dto.response.loan.LoanResponse;
import com.gautam.bank.entity.auth.User;
import com.gautam.bank.entity.customer.Customer;
import com.gautam.bank.entity.loan.Loan;
import com.gautam.bank.entity.sequence.CodeSequence;
import com.gautam.bank.enums.LoanStatus;
import com.gautam.bank.exception.ResourceNotFoundException;
import com.gautam.bank.repository.CustomerRepository;
import com.gautam.bank.repository.LoanRepository;
import com.gautam.bank.repository.UserRepository;
import com.gautam.bank.service.CodeSequenceService;
import com.gautam.bank.service.LoanService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final CodeSequenceService codeSequenceService;

    @Override
    @Transactional
    public LoanResponse applyLoan(ApplyLoanRequest request) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

        Loan loan = Loan.builder()
                .customer(customer)
                .loanType(request.getLoanType())
                .loanAmount(request.getLoanAmount())
                .tenureMonths(request.getTenureMonths())
                .status(LoanStatus.PENDING)
                .build();
        // loan = loanRepository.save(loan);
        // Generate Loan Number before saving
        loan.setLoanNumber(codeSequenceService.generateLoanNumber());

        loan = loanRepository.save(loan);
        return mapToResponse(loan);
    }

    @Override
    @Transactional
    public List<LoanResponse> getPendingLoans() {

        List<Loan> loans = loanRepository.findByStatus(
                LoanStatus.PENDING);

        return loans.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private LoanResponse mapToResponse(Loan loan) {

        return LoanResponse.builder()
                .id(loan.getId())
                .loanNumber(loan.getLoanNumber())
                .customerCode(loan.getCustomer().getCustomerCode())
                .customerName(loan.getCustomer().getFirstName() + " " + loan.getCustomer().getLastName())
                .loanType(loan.getLoanType())
                .loanAmount(loan.getLoanAmount())
                .interestRate(loan.getInterestRate())
                .tenureMonths(loan.getTenureMonths())
                .emiAmount(loan.getEmiAmount())
                .approvedAmount(loan.getApprovedAmount())
                .disbursedAmount(loan.getDisbursedAmount())
                .remainingAmount(loan.getRemainingAmount())
                .status(loan.getStatus())
                .remarks(loan.getRemarks())
                .approvedBy(loan.getApprovedBy() != null
                        ? loan.getApprovedBy().getEmployeeCode()
                        : null)
                .approvedAt(loan.getApprovedAt())
                .disbursedAt(loan.getDisbursedAt())
                .createdAt(loan.getCreatedAt())
                .build();
    }
}
