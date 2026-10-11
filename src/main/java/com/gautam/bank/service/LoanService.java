package com.gautam.bank.service;

import java.util.List;

import com.gautam.bank.dto.request.loan.ApplyLoanRequest;
import com.gautam.bank.dto.response.loan.LoanResponse;

public interface LoanService {
    
    LoanResponse applyLoan(ApplyLoanRequest request);

    List<LoanResponse> getPendingLoans();
}
