package com.gautam.bank.dto.request.loan;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 

public class PayEmiRequest {
    
    @NotBlank 
    private String loanNumber;

    @NotBlank 
    private String accountNumber;
}
