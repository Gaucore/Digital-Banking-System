package com.gautam.bank.dto.request.account;

import com.gautam.bank.enums.AccountType;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank (message = "Customer code is required")
    private String customerCode;

    @NotNull(message = "Account type is required")
    private AccountType accountType;
}
