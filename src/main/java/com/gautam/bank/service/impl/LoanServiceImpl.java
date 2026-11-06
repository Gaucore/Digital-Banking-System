package com.gautam.bank.service.impl;

import com.gautam.bank.controller.loan.LoanController;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Attribute.Use;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gautam.bank.dto.request.loan.ApplyLoanRequest;
import com.gautam.bank.dto.request.loan.ApproveLoanRequest;
import com.gautam.bank.dto.request.loan.DisburseLoanRequest;
import com.gautam.bank.dto.request.loan.PayEmiRequest;
import com.gautam.bank.dto.request.loan.RejectLoanRequest;
import com.gautam.bank.dto.response.loan.LoanClosureCertificateResponse;
import com.gautam.bank.dto.response.loan.LoanDashboardResponse;
import com.gautam.bank.dto.response.loan.LoanRepaymentResponse;
import com.gautam.bank.dto.response.loan.LoanResponse;
import com.gautam.bank.entity.account.Account;
import com.gautam.bank.entity.auth.User;
import com.gautam.bank.entity.customer.Customer;
import com.gautam.bank.entity.employee.Employee;
import com.gautam.bank.entity.loan.Loan;
import com.gautam.bank.entity.loan.LoanRepayment;
import com.gautam.bank.entity.sequence.CodeSequence;
import com.gautam.bank.entity.transaction.Transaction;
import com.gautam.bank.enums.LoanStatus;
import com.gautam.bank.enums.TransactionType;
import com.gautam.bank.exception.ResourceNotFoundException;
import com.gautam.bank.repository.AccountRepository;
import com.gautam.bank.repository.CustomerRepository;
import com.gautam.bank.repository.EmployeeRepository;
import com.gautam.bank.repository.LoanRepaymentRepository;
import com.gautam.bank.repository.LoanRepository;
import com.gautam.bank.repository.TransactionRepository;
import com.gautam.bank.repository.UserRepository;
import com.gautam.bank.service.CodeSequenceService;
import com.gautam.bank.service.LoanService;
import com.gautam.bank.util.loan.EmiCalculator;

// import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

        private final LoanRepository loanRepository;
        private final CustomerRepository customerRepository;
        private final UserRepository userRepository;
        private final CodeSequenceService codeSequenceService;
        private final EmployeeRepository employeeRepository;
        private final EmiCalculator emiCalculator;
        private final AccountRepository accountRepository;
        private final TransactionRepository transactionRepository;
        private final LoanRepaymentRepository repaymentRepository;

    

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
                                .customerName(loan.getCustomer().getFirstName() + " "
                                                + loan.getCustomer().getLastName())
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

        @Override
        @Transactional
        public LoanResponse approveLoan(ApproveLoanRequest request) {
                Loan loan = loanRepository.findByLoanNumber(request.getLoanNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Loan not found."));
                if (loan.getStatus() != LoanStatus.PENDING) {
                        throw new IllegalArgumentException("Only Pending loans can be approved.");
                }

                String username = SecurityContextHolder.getContext().getAuthentication().getName();

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                Employee employee = employeeRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Employee not found."));

                // BigDecimal emi =
                // request.getApprovedAmount().divide(BigDecimal.valueOf(request.getTenureMonths()),
                // 2,
                // RoundingMode.HALF_UP);

                BigDecimal emi = emiCalculator.calcuate(request.getApprovedAmount(), request.getInterestRate(),
                                request.getTenureMonths());
                loan.setApprovedAmount(request.getApprovedAmount());
                loan.setInterestRate(request.getInterestRate());
                loan.setTenureMonths(request.getTenureMonths());
                loan.setEmiAmount(emi);
                loan.setRemainingAmount(request.getApprovedAmount());

                loan.setStatus(LoanStatus.APPROVED);

                loan.setApprovedBy(employee);
                loan.setApprovedAt(LocalDateTime.now());

                loan.setRemarks(request.getRemarks());

                // Save Updated Loan
                loan = loanRepository.save(loan);

                // Return Response
                return mapToResponse(loan);
        }

        @Override
        @Transactional
        public LoanResponse rejectLoan(RejectLoanRequest request) {
                Loan loan = loanRepository.findByLoanNumber(request.getLoanNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Loan  not found."));

                if (loan.getStatus() != LoanStatus.PENDING) {
                        throw new IllegalArgumentException("Only Pending loan can be rejected.");
                }

                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

                Employee employee = employeeRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Employee not found."));

                loan.setStatus(LoanStatus.REJECTED);
                loan.setRemarks(request.getRemarks());
                loan.setApprovedBy(employee);
                loan.setApprovedAt(LocalDateTime.now());

                loan = loanRepository.save(loan);

                return mapToResponse(loan);
        }

        @Override
        @Transactional
        public LoanResponse disburseLoan(DisburseLoanRequest request) {
                Loan loan = loanRepository.findByLoanNumber(request.getLoanNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Loan not found."));

                if (loan.getStatus() != LoanStatus.APPROVED) {
                        throw new IllegalArgumentException("Only approval loans can be disbursed.");
                }

                Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));

                if (!account.getCustomer().getId().equals(loan.getCustomer().getId())) {
                        throw new IllegalArgumentException("Loan and account belong to different customers.");
                }

                BigDecimal newBalance = account.getBalance().add(loan.getApprovedAmount());

                account.setBalance(newBalance);

                account = accountRepository.save(account);

                // Create Transaction
                Transaction transaction = Transaction.builder()
                                .account(account)
                                .transactionNumber(codeSequenceService.generateTransactionNumber())
                                .transactionType(TransactionType.DEPOSIT)
                                .amount(loan.getApprovedAmount())
                                .previousBalance(newBalance.subtract(loan.getApprovedAmount()))
                                .currentBalance(newBalance)
                                .remarks("Loan Disbursement")
                                .build();
                transactionRepository.save(transaction);
                // Update Loan
                loan.setDisbursedAmount(loan.getApprovedAmount());
                loan.setRemainingAmount(loan.getApprovedAmount());
                loan.setStatus(LoanStatus.DISBURSED);
                loan.setDisbursedAt(LocalDateTime.now());
                loan = loanRepository.save(loan);

                return mapToResponse(loan);
        }

        @Override
        @Transactional(readOnly = true)
        public List<LoanResponse> getMyLoans() {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

                List<Loan> loans = loanRepository.findByCustomer(customer);

                return loans.stream().map(this::mapToResponse).toList();
        }

        @Override
        @Transactional(readOnly = true)
        public LoanResponse getLoanByLoanNumber(String loanNumber) {
                Loan loan = loanRepository.findByLoanNumber(loanNumber)
                                .orElseThrow(() -> new ResourceNotFoundException("Loan not found."));
                return mapToResponse(loan);
        }

        @Override
        @Transactional(readOnly = true)
        public List<LoanResponse> getLoansByStatus(LoanStatus status) {
                return loanRepository.findByStatus(status)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public List<LoanResponse> getMyLoansByStatus(LoanStatus status) {

                String username = SecurityContextHolder.getContext().getAuthentication().getName();

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

                Customer customer = customerRepository.findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

                return loanRepository.findByCustomerAndStatus(customer, status)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        @Transactional
        public LoanResponse payEmi(PayEmiRequest request) {
                Loan loan = loanRepository.findByLoanNumber(request.getLoanNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Loan not found."));

                if (loan.getStatus() != LoanStatus.DISBURSED) {
                        throw new IllegalArgumentException("Only disbursed loans can have EMI payments.");
                }

                Account account = accountRepository
                                .findByAccountNumber(request.getAccountNumber())
                                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));

                if (!loan.getCustomer().getId()
                                .equals(account.getCustomer().getId())) {

                        throw new IllegalArgumentException(
                                        "Loan and account belong to different customers.");
                }

                if (account.getBalance().compareTo(loan.getEmiAmount()) < 0) {

                        throw new IllegalArgumentException(
                                        "Insufficient balance.");
                }

                BigDecimal previousBalance = account.getBalance();
                BigDecimal newBalance = previousBalance.subtract(loan.getEmiAmount());
                account.setBalance(newBalance);
                accountRepository.save(account);

                loan.setRemainingAmount(loan.getRemainingAmount().subtract(loan.getEmiAmount()));
                LoanRepayment repayment = LoanRepayment.builder()
                                .loan(loan)
                                .amount(loan.getEmiAmount())
                                .installmentNumber(
                                                (int) repaymentRepository.countByLoan(loan) + 1)
                                .paymentDate(LocalDateTime.now())
                                .build();

                repaymentRepository.save(repayment);

                Transaction transaction = Transaction.builder()
                                .transactionNumber(
                                                codeSequenceService.generateTransactionNumber())
                                .account(account)
                                .transactionType(TransactionType.LOAN_REPAYMENT)
                                .amount(loan.getEmiAmount())
                                .previousBalance(previousBalance)
                                .currentBalance(account.getBalance())
                                .remarks("Loan EMI Payment")
                                .build();

                transactionRepository.save(transaction);

                if (loan.getRemainingAmount()
                                .compareTo(BigDecimal.ZERO) <= 0) {

                        loan.setRemainingAmount(BigDecimal.ZERO);
                        loan.setStatus(LoanStatus.CLOSED);
                        loan.setClosedAt(LocalDateTime.now());
                }

                loan = loanRepository.save(loan);
                return mapToResponse(loan);
        }

        @Override
        @Transactional(readOnly = true)
        public List<LoanRepaymentResponse> getLoanRepaymentHistory(
                        String loanNumber) {

                Loan loan = loanRepository
                                .findByLoanNumber(loanNumber)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Loan not found."));

                String username = SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName();

                User user = userRepository
                                .findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User not found."));

                Customer customer = customerRepository
                                .findByUser(user)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Customer not found."));

                if (!loan.getCustomer().getId()
                                .equals(customer.getId())) {

                        throw new IllegalStateException(
                                        "Unauthorized access.");
                }

                return repaymentRepository
                                .findByLoan(loan)
                                .stream()
                                .map(repayment -> LoanRepaymentResponse.builder()
                                                .installmentNumber(repayment.getInstallmentNumber())
                                                .amount(repayment.getAmount())
                                                .paymentDate(repayment.getPaymentDate())
                                                .build())
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public LoanDashboardResponse getDashboard() {
                return LoanDashboardResponse.builder()
                                .totalLoans(loanRepository.count())
                                .pendingLoans(loanRepository.countByStatus(LoanStatus.PENDING))
                                .approvedLoans(loanRepository.countByStatus(LoanStatus.APPROVED))
                                .disbursedLoans(loanRepository.countByStatus(LoanStatus.DISBURSED))
                                .closedLoans(loanRepository.countByStatus(LoanStatus.CLOSED))
                                .totalRequestedAmount(loanRepository.getTotalRequestedAmount())
                                .totalApprovedAmount(loanRepository.getTotalApprovedAmount())
                                .totalDisbursedAmount(loanRepository.getTotalDisbursedAmount())
                                .outstandingAmount(loanRepository.getOutstandingAmount())
                                .build();
        }

        @Override
        @Transactional(readOnly = true)
        public LoanClosureCertificateResponse getClosureCertificate(String loanNumber) {

                Loan loan = loanRepository.findByLoanNumber(loanNumber)
                                .orElseThrow(() -> new ResourceNotFoundException("Loan not found."));
                if (loan.getStatus() != LoanStatus.CLOSED) {
                        throw new IllegalStateException("Loan is not closed yet.");
                }

                BigDecimal totalPaid = repaymentRepository.findByLoan(loan)
                                .stream()
                                .map(LoanRepayment::getAmount)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                return LoanClosureCertificateResponse.builder()
                                .loanNumber(loan.getLoanNumber())
                                .customerCode(loan.getCustomer().getCustomerCode())
                                .customerName(loan.getCustomer().getFirstName() + loan.getCustomer().getLastName())
                                .loanAmount(loan.getLoanAmount())
                                .approvedAmount(loan.getApprovedAmount())
                                .totalPaidAmount(totalPaid)
                                .disbursedAt(loan.getDisbursedAt())
                                .closedAt(loan.getClosedAt())
                                .certificateMessage("No outstanding dues. Loan successfully closed.")
                                .build();
        }
}
