package com.gautam.bank.dto.request.statement;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class MonthlyStatementRequest {

    @NotBlank (message = "Account number is required")
    private String accountNumber;

    @NotNull (message = "Year is required")
    @Min (value = 2000,message = "Invalid year")
    @Max (value = 2100,message = "Invalid year")
    private Integer year;

    @NotNull (message = "Month is required")
    @Min (value = 1,message = "Month must be between 1 and 12")
    @Max (value = 12,message = "Month must be between 1 and 12")
    private Integer month;
    
}
