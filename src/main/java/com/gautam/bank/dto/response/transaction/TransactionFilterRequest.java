package com.gautam.bank.dto.response.transaction;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.gautam.bank.enums.TransactionType;

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
public class TransactionFilterRequest {

    private String accountNumber;
    private TransactionType transactionType;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate toDate; 
    
}
