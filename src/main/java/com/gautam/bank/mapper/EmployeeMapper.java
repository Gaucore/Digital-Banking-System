package com.gautam.bank.mapper;

import org.springframework.stereotype.Component;

import com.gautam.bank.dto.request.employee.CreateEmployeeRequest;
import com.gautam.bank.dto.response.employee.EmployeeResponse;
import com.gautam.bank.entity.auth.User;
import com.gautam.bank.entity.employee.Employee;

@Component
public class EmployeeMapper {

    public User toUser(CreateEmployeeRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        return user;
    }

    public Employee toEmployee(CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setMobileNumber(request.getMobileNumber());
        employee.setDesignation(request.getDesignation());
        employee.setBranch(request.getBranch());
        employee.setSalary(request.getSalary());
        employee.setAddress(request.getAddress());
        return employee;
    }

    public EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder().id(employee.getId()).employeeCode(employee.getEmployeeCode())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .fullName(employee.getFirstName() + " " + employee.getLastName())
                .email(employee.getEmail())
                .mobileNumber(employee.getMobileNumber())
                .designation(employee.getDesignation())
                .branch(employee.getBranch())
                .joiningDate(employee.getJoiningDate())
                .address(employee.getAddress())
                .username(employee.getUser().getUsername())
                .role(employee.getUser().getRole().name())
                .status(employee.getUser().getStatus().name())
                .build();
    }
}
