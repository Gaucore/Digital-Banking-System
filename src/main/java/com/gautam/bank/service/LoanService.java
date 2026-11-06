package com.gautam.bank.service;

import java.util.List;

import com.gautam.bank.dto.request.loan.ApplyLoanRequest;
import com.gautam.bank.dto.request.loan.ApproveLoanRequest;
import com.gautam.bank.dto.request.loan.DisburseLoanRequest;
import com.gautam.bank.dto.request.loan.PayEmiRequest;
import com.gautam.bank.dto.request.loan.RejectLoanRequest;
import com.gautam.bank.dto.response.loan.LoanClosureCertificateResponse;
import com.gautam.bank.dto.response.loan.LoanDashboardResponse;
import com.gautam.bank.dto.response.loan.LoanRepaymentResponse;
import com.gautam.bank.dto.response.loan.LoanResponse;
import com.gautam.bank.enums.LoanStatus;

public interface LoanService {

    LoanResponse applyLoan(ApplyLoanRequest request);

    List<LoanResponse> getPendingLoans();

    LoanResponse approveLoan(ApproveLoanRequest request);

    LoanResponse rejectLoan(RejectLoanRequest request);

    LoanResponse disburseLoan(DisburseLoanRequest request);

    List<LoanResponse> getMyLoans();

    LoanResponse getLoanByLoanNumber(String loanNumber);

    List<LoanResponse> getLoansByStatus(LoanStatus status);

    List<LoanResponse> getMyLoansByStatus(LoanStatus status);

    LoanResponse payEmi(PayEmiRequest request);

    List<LoanRepaymentResponse> getLoanRepaymentHistory(String loanNumber);

    LoanDashboardResponse getDashboard();

    LoanClosureCertificateResponse getClosureCertificate(String loanNumber);
}
