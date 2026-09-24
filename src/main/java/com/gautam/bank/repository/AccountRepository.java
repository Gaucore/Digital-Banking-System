package com.gautam.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gautam.bank.entity.account.Account;
import com.gautam.bank.enums.AccountStatus;
import com.gautam.bank.enums.AccountType;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    Optional<Account> findByCustomerId(String customerId);

    List<Account> findByStatus(AccountStatus status);

    List<Account> findByAccountType(AccountType accountType);

    boolean existsByAccountNumber(String accountNumber);

}
