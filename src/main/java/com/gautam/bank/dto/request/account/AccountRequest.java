package com.gautam.bank.dto.request.account;

import com.gautam.bank.enums.AccountType;

import jakarta.validation.constraints.NotNull;
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
public class AccountRequest {

    @NotNull(message = "Customer id is required")
    private Long customerId;

    @NotNull(message = "Account Type is required")
    private AccountType accountType;
}
