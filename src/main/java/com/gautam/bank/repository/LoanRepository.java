package com.gautam.bank.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gautam.bank.entity.loan.Loan;
import com.gautam.bank.enums.LoanStatus;
import com.gautam.bank.dto.response.loan.LoanRepaymentResponse;
import com.gautam.bank.entity.customer.Customer;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByLoanNumber(String loanNumber);

    List<Loan> findByCustomer(Customer customer);

    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByCustomerAndStatus(Customer customer, LoanStatus status);

    long countByStatus(LoanStatus status);

    @Query("""
            SELECT COALESCE(SUM(l.loanAmount),0)
            FROM Loan l
            """)
    BigDecimal getTotalRequestedAmount();

    @Query("""
            SELECT COALESCE(SUM(l.approvedAmount),0)
            FROM Loan l
            """)
    BigDecimal getTotalApprovedAmount();

    @Query("""
            SELECT COALESCE(SUM(l.disbursedAmount),0)
            FROM Loan l
            """)
    BigDecimal getTotalDisbursedAmount();

    @Query("""
            SELECT COALESCE(SUM(l.remainingAmount),0)
            FROM Loan l
            WHERE l.status='DISBURSED'
            OR l.status='ACTIVE'
            """)
    BigDecimal getOutstandingAmount();

}
