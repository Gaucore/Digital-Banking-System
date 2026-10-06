package com.gautam.bank.service;

import com.gautam.bank.dto.request.employee.CreateEmployeeRequest;
import com.gautam.bank.dto.response.employee.EmployeeResponse;

public interface EmployeeService {

    EmployeeResponse createEmployee(CreateEmployeeRequest request);
}
