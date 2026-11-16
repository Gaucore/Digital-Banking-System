package com.gautam.bank.dto.response.loan;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.gautam.bank.enums.LoanStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingLoanResponse {

    private Long loanId;
    private String loanNumber;
    private String customerCode;
    private String customerName;
    private String loanType;
    private BigDecimal requestedAmount;
    private BigDecimal interestRate;
    private Integer tenure;
    private String branch;
    private LocalDate appliedDate;
    private LoanStatus status;

}
