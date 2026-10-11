package com.gautam.bank.dto.response.loan;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gautam.bank.enums.LoanStatus;
import com.gautam.bank.enums.LoanType;

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
public class LoanResponse {

    private Long id;
    private String loanNumber;
    private String customerCode;
    private String customerName;
    private LoanType loanType;
    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private Integer tenureMonths;
    private BigDecimal emiAmount;
    private BigDecimal approvedAmount;
    private BigDecimal disbursedAmount;
    private BigDecimal remainingAmount;
    private LoanStatus status;
    private String remarks;
    private String approvedBy;
    private LocalDateTime approvedAt;
    private LocalDateTime disbursedAt;
    private LocalDateTime createdAt;
}
