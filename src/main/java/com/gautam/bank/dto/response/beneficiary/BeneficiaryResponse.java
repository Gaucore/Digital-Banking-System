package com.gautam.bank.dto.response.beneficiary;

import java.time.LocalDate;

import com.gautam.bank.enums.AccountType;

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
public class BeneficiaryResponse {

    private String beneficiaryCode;
    private String nickname;
    private String accountNumber;
    private String accountHolderName;
    private AccountType accountType;
    private LocalDate createdAt;
}
