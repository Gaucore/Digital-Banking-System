package com.gautam.bank.dto.request.loan;

import java.math.BigDecimal;

import com.gautam.bank.enums.LoanType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class ApplyLoanRequest {
    
    @NotNull (message = "Loan type is required")
    private LoanType loanType;

    @NotNull (message = "Loan amount is required")
    @DecimalMin (value = "1000.00",message = "Loan amount must be at least 1000")
    private BigDecimal loanAmount;

    @NotNull (message = "Tenure is required")
    @Positive (message = "Tenure must be greater than zero")
    private Integer tenureMonths;
}
