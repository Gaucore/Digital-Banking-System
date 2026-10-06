package com.gautam.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gautam.bank.entity.auth.User;
import com.gautam.bank.entity.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerCode(String customerCode);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhoneAndIdNot(
            String phone,
            Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhone(String phone);

    Optional<Customer> findByUser(User user);

}
