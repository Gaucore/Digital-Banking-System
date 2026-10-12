package com.gautam.bank.dto.request.loan;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ApproveLoanRequest {

    @NotBlank (message = "Loan number is required")
    private String loanNumber;

    @NotNull(message = "Approved amount is required")
    @DecimalMin(value = "1000.00")
    private BigDecimal approvedAmount;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.01")
    private BigDecimal interestRate;

    @NotNull(message = "Tenure is required")
    @Positive
    private Integer tenureMonths;

    private String remarks;
}
