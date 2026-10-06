package com.gautam.bank.service.impl;

import java.time.LocalDate;

import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gautam.bank.dto.request.employee.CreateEmployeeRequest;
import com.gautam.bank.dto.response.employee.EmployeeResponse;
import com.gautam.bank.entity.auth.User;
import com.gautam.bank.entity.employee.Employee;
import com.gautam.bank.enums.UserRole;
import com.gautam.bank.enums.UserStatus;
import com.gautam.bank.exception.DuplicateResourceException;
import com.gautam.bank.mapper.EmployeeMapper;
import com.gautam.bank.repository.EmployeeRepository;
import com.gautam.bank.repository.UserRepository;
import com.gautam.bank.service.CodeSequenceService;
import com.gautam.bank.service.EmployeeService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CodeSequenceService codeSequenceService;

    @Override
    @Transactional
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Employee email already exists");
        }

        if (employeeRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateResourceException("Employee mobile no. already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("username already exists");
        }

        // user created first
        User user = employeeMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(UserRole.EMPLOYEE);
        user.setStatus(UserStatus.ACTIVE);
        User savedUser = userRepository.save(user);

        Employee employee = employeeMapper.toEmployee(request);

        employee.setUser(savedUser);
        employee.setJoiningDate(LocalDate.now());

        employee.setEmployeeCode(codeSequenceService.generateEmployeeCode());

        Employee saveEmployee = employeeRepository.save(employee);

        // saveEmployee.setEmployeeCode(String.format("EMP%06d", saveEmployee.getId()));

        // saveEmployee = employeeRepository.save(saveEmployee);

        return employeeMapper.toResponse(saveEmployee);
    }

}
