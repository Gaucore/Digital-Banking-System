package com.gautam.bank.dto.request.statement;

import java.time.LocalDate;

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

public class StatementRequest {

    @NotBlank (message = "Account number is required")
    private String accountNumber;

    @NotNull (message = "From date is required")
    private LocalDate fromDate;

    @NotNull (message = "To date is required")
    private LocalDate toDate;
    
}
