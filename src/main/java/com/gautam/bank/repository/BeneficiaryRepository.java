package com.gautam.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gautam.bank.entity.account.Account;
import com.gautam.bank.entity.beneficiary.Beneficiary;
import com.gautam.bank.entity.customer.Customer;


@Repository 
public interface BeneficiaryRepository extends JpaRepository<Beneficiary,Long> {

        Optional<Beneficiary> findByBeneficiaryCode(String beneficiaryCode);

        List<Beneficiary> findByCustomerOrderByCreatedAtDesc(Customer customer);

        Optional<Beneficiary> findByCustomerAndAccount(Customer customer,Account account);

    
} 
