package com.gautam.bank.repository;

import java.util.List;
import java.util.Optional;

import javax.swing.Spring;

import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gautam.bank.entity.employee.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    Optional<Employee> findByEmployeeCode(String employeeCode);

    List<Employee> findByBranch(String branch);

    List<Employee> findByDesignation(String designation);

    // A derived query method is a method whose SQL query is automatically generated
    // by Spring Data JPA based on the method name, such as findByEmail() or
    // existsByMobileNumber

}
