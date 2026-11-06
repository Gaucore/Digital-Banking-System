package com.gautam.bank.dto.response.loan;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class LoanClosureCertificateResponse {
    
    private String loanNumber;
    private String customerCode;
    private String customerName;
    private BigDecimal loanAmount;
    private BigDecimal approvedAmount;
    private BigDecimal totalPaidAmount;
    private LocalDateTime disbursedAt;
    private LocalDateTime closedAt;
    private String certificateMessage;
}
