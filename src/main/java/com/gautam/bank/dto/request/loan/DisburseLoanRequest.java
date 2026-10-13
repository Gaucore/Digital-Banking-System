package com.gautam.bank.dto.request.loan;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class DisburseLoanRequest {

    @NotBlank (message = "Loan number is required")
    private String loanNumber;

    @NotBlank (message = "Account number is required")
    private String accountNumber;
    
}
