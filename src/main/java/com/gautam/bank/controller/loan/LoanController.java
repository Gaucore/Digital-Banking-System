package com.gautam.bank.controller.loan;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gautam.bank.dto.request.loan.ApplyLoanRequest;
import com.gautam.bank.dto.response.loan.LoanResponse;
import com.gautam.bank.service.LoanService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/apply")
    public ResponseEntity<LoanResponse> applyLoan(@Valid @RequestBody ApplyLoanRequest request) {
        LoanResponse response = loanService.applyLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
