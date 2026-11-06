package com.gautam.bank.controller.loan;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gautam.bank.dto.request.loan.ApplyLoanRequest;
import com.gautam.bank.dto.request.loan.PayEmiRequest;
import com.gautam.bank.dto.response.loan.LoanClosureCertificateResponse;
import com.gautam.bank.dto.response.loan.LoanRepaymentResponse;
import com.gautam.bank.dto.response.loan.LoanResponse;
import com.gautam.bank.enums.LoanStatus;
import com.gautam.bank.service.LoanService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping
    public ResponseEntity<List<LoanResponse>> getMyLoans() {
        List<LoanResponse> response = loanService.getMyLoans();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LoanResponse>> getMyLoansByStatus(@PathVariable LoanStatus status) {

        return ResponseEntity.ok(
                loanService.getMyLoansByStatus(
                        status));
    }

    @PostMapping("/pay-emi")
    public ResponseEntity<LoanResponse> payEmi(@Valid @RequestBody PayEmiRequest request) {

        LoanResponse response = loanService.payEmi(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{loanNumber}/repayments")
    public ResponseEntity<List<LoanRepaymentResponse>> getLoanRepaymentHistory(
            @PathVariable String loanNumber) {

        return ResponseEntity.ok(
                loanService.getLoanRepaymentHistory(
                        loanNumber));
    }

    @GetMapping("/{loanNumber}/closure-certificate")
    public ResponseEntity<LoanClosureCertificateResponse> getClosureCertificate(@PathVariable String loanNumber) {
        return ResponseEntity.ok(loanService.getClosureCertificate(loanNumber));
    }

}
