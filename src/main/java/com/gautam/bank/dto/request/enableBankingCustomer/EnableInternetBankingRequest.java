package com.gautam.bank.dto.request.enableBankingCustomer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class EnableInternetBankingRequest {

    @NotNull (message = "Customer id is required")
    private Long customerId;

    @NotBlank (message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password; 

}
