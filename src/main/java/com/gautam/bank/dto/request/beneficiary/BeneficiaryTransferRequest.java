package com.gautam.bank.dto.request.beneficiary;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class BeneficiaryTransferRequest {


    @NotBlank (message = "Source account number is required")
    private String fromAccountNumber;

    @NotBlank (message = "Beneficiary code is required")
    private String beneficiaryCode;
    
    @NotNull (message = "Amount is required")
    @DecimalMin (value = "0.01",message = "Amount must be greater than zero")
    private BigDecimal amount;

    private String remarks;
    
}
