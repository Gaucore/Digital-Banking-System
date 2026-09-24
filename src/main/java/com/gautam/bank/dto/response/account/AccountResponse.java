package com.gautam.bank.dto.response.account;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.gautam.bank.enums.AccountStatus;

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
public class AccountResponse {

    private Long id;
    private String accountNumber;
    private String customerCode;
    private String customerName;
    private BigDecimal balance;
    private AccountStatus status;
    private LocalDate createdAt;
}
