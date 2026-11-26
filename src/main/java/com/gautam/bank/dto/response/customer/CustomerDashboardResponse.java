package com.gautam.bank.dto.response.customer;

import java.math.BigDecimal;

import com.gautam.bank.dto.response.account.CustomerAccountResponse;
import com.gautam.bank.dto.response.customerProfile.CustomerProfileResponse;
import com.gautam.bank.dto.response.transaction.CustomerTransactionResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDashboardResponse {

    // private String customerCode;
    // private String fullName;
    // private String email;
    // private String phone;
    // private String accountNumber;
    // private String accountType;
    // private BigDecimal availableBalance;
    // private String accountStatus;
    // private Long totalAccounts;
    // private Long totalTransactions;

    private CustomerProfileResponse customer;
    private List<CustomerAccountResponse> accounts;
    private List<CustomerTransactionResponse> recentTransactions;

}
