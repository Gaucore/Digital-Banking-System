package com.gautam.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gautam.bank.entity.transaction.Transaction;
import com.gautam.bank.enums.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionNumber(String transactionNumber);

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByTransactionType(TransactionType transactionType);

    List<Transaction> findByAccountId(Long accountId, Sort sort);

}
