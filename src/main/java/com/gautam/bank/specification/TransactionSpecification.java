package com.gautam.bank.specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gautam.bank.dto.response.transaction.TransactionFilterRequest;
import com.gautam.bank.entity.customer.Customer;
import com.gautam.bank.entity.transaction.Transaction;

import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransactionSpecification {

        public static Specification<Transaction> filter(
                        TransactionFilterRequest request, Customer customer) {

                return (root, query, criteriaBuilder) -> {

                        List<Predicate> predicates = new ArrayList<>();

                        predicates.add(criteriaBuilder.equal(root.get("account").get("customer"), customer));

                        // ==========================================================
                        // Account Number Filter
                        // ==========================================================
                        if (request.getAccountNumber() != null
                                        && !request.getAccountNumber().isBlank()) {

                                predicates.add(
                                                criteriaBuilder.equal(
                                                                root.get("account")
                                                                                .get("accountNumber"),
                                                                request.getAccountNumber()));
                        }

                        // ==========================================================
                        // Transaction Type Filter
                        // ==========================================================
                        if (request.getTransactionType() != null) {

                                predicates.add(
                                                criteriaBuilder.equal(
                                                                root.get("transactionType"),
                                                                request.getTransactionType()));
                        }

                        // ==========================================================
                        // From Date Filter
                        // ==========================================================
                        if (request.getFromDate() != null) {

                                LocalDateTime from = request.getFromDate()
                                                .atStartOfDay();

                                predicates.add(
                                                criteriaBuilder.greaterThanOrEqualTo(
                                                                root.get("createdAt"),
                                                                from));
                        }

                        // ==========================================================
                        // To Date Filter
                        // ==========================================================
                        if (request.getToDate() != null) {

                                LocalDateTime to = request.getToDate()
                                                .plusDays(1)
                                                .atStartOfDay();

                                predicates.add(
                                                criteriaBuilder.lessThan(
                                                                root.get("createdAt"),
                                                                to));
                        }

                        // ==========================================================
                        // Order By Latest Transaction
                        // ==========================================================
                        query.orderBy(
                                        criteriaBuilder.desc(root.get("createdAt")));

                        // ==========================================================
                        // Return All Conditions
                        // ==========================================================
                        return criteriaBuilder.and(
                                        predicates.toArray(new Predicate[0]));
                };
        }
}