package com.gautam.bank.dto.response.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.gautam.bank.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    private String transactionNumber;
    private TransactionType transactionType;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private BigDecimal previousBalance;
    private BigDecimal currentBalance;
    private String remarks;
    private LocalDate createdAt;

}
