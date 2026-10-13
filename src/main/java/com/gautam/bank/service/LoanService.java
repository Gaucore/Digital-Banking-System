package com.gautam.bank.service;

import java.util.List;

import com.gautam.bank.dto.request.loan.ApplyLoanRequest;
import com.gautam.bank.dto.request.loan.ApproveLoanRequest;
import com.gautam.bank.dto.request.loan.DisburseLoanRequest;
import com.gautam.bank.dto.request.loan.RejectLoanRequest;
import com.gautam.bank.dto.response.loan.LoanResponse;

public interface LoanService {
    
    LoanResponse applyLoan(ApplyLoanRequest request);

    List<LoanResponse> getPendingLoans();

    LoanResponse approveLoan(ApproveLoanRequest request);

    LoanResponse rejectLoan(RejectLoanRequest request);

    LoanResponse disburseLoan(DisburseLoanRequest request);
}
