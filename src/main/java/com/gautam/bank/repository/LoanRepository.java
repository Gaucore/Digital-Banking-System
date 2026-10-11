package com.gautam.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gautam.bank.entity.loan.Loan;
import com.gautam.bank.enums.LoanStatus;
import com.gautam.bank.entity.customer.Customer;



@Repository 
public interface LoanRepository extends JpaRepository<Loan,Long>{

    Optional<Loan> findByLoanNumber(String loanNumber);

    List<Loan> findByCustomer(Customer customer);

    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByCustomerAndStatus(Customer customer,LoanStatus status);
} 
