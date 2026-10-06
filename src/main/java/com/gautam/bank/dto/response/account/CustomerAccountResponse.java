package com.gautam.bank.dto.response.account;

import java.math.BigDecimal;

import com.gautam.bank.enums.AccountStatus;
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
public class CustomerAccountResponse {
    
    private String accountNumber;
    private AccountType accountType;
    private  BigDecimal balance;
    private  AccountStatus accountStatus;
}
