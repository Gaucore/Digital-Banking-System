package com.gautam.bank.dto.response.statement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class StatementResponse {

    private String transactionNumber;
    private TransactionType transactionType;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private BigDecimal previousBalance;
    private BigDecimal currentBalance;
    private String remarks;
    private LocalDateTime transactionDate;
    
}
