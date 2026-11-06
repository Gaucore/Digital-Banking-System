package com.gautam.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gautam.bank.entity.loan.Loan;
import com.gautam.bank.entity.loan.LoanRepayment;

@Repository
public interface LoanRepaymentRepository extends JpaRepository<LoanRepayment, Long> {
    List<LoanRepayment> findByLoan(Loan loan);

    List<LoanRepayment> findByLoanOrderByInstallmentNumberAsc(Loan loan);

    long countByLoan(Loan loan);
}
