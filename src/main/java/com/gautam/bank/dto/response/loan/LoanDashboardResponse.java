package com.gautam.bank.dto.response.loan;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanDashboardResponse {

    private Long totalLoans;
    private Long pendingLoans;
    private Long approvedLoans;
    private Long rejectedLoans;
    private Long disbursedLoans;
    private Long closedLoans;
    private BigDecimal totalRequestedAmount;
    private BigDecimal totalApprovedAmount;
    private BigDecimal totalDisbursedAmount;
    private BigDecimal outstandingAmount;

}
