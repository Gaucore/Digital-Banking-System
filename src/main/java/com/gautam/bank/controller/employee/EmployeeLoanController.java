package com.gautam.bank.controller.employee;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gautam.bank.dto.request.loan.ApplyLoanRequest;
import com.gautam.bank.dto.request.loan.ApproveLoanRequest;
import com.gautam.bank.dto.request.loan.DisburseLoanRequest;
import com.gautam.bank.dto.response.loan.LoanDashboardResponse;
import com.gautam.bank.dto.response.loan.LoanResponse;
import com.gautam.bank.entity.loan.Loan;
import com.gautam.bank.enums.LoanStatus;
import com.gautam.bank.service.LoanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee/loans")
public class EmployeeLoanController {

    private final LoanService loanService;

    @GetMapping("/pending")
    public ResponseEntity<List<LoanResponse>> getPendingLoans() {
        List<LoanResponse> response = loanService.getPendingLoans();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve")
    public ResponseEntity<LoanResponse> approveLoan(@Valid @RequestBody ApproveLoanRequest request) {
        LoanResponse response = loanService.approveLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/disburse")
    public ResponseEntity<LoanResponse> disburseLoan(@Valid @RequestBody DisburseLoanRequest request) {
        LoanResponse response = loanService.disburseLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{loanNumber}")
    public ResponseEntity<LoanResponse> getLoan(@PathVariable String loanNumber) {
        return ResponseEntity.ok(loanService.getLoanByLoanNumber(
                loanNumber));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LoanResponse>> getLoansByStatus(@PathVariable LoanStatus status) {
        return ResponseEntity.ok(loanService.getLoansByStatus(status));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<LoanDashboardResponse> getDashboard() {
        LoanDashboardResponse response = loanService.getDashboard();
        return ResponseEntity.ok(response);
    }

}
