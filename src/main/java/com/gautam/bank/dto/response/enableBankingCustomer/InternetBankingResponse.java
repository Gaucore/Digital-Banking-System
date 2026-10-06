package com.gautam.bank.dto.response.enableBankingCustomer;

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
public class InternetBankingResponse {

    private String customerCode;
    private String username;
    private String role;
    private String message;
    
}
