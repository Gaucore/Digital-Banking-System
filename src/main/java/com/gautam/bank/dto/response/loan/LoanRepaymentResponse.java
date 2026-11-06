package com.gautam.bank.dto.response.loan;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class LoanRepaymentResponse {

    private Integer installmentNumber;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
}
