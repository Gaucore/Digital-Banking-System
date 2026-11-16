package com.gautam.bank.dto.request.loan;

import jakarta.validation.constraints.NotBlank;
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
public class PreCloseLoanRequest {

    @NotBlank 
    private String loanNumber;

    @NotBlank 
    private String accountNumber;
    
}
