package com.gautam.bank.entity.loan;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gautam.bank.entity.BaseEntity;
import com.gautam.bank.entity.customer.Customer;
import com.gautam.bank.entity.employee.Employee;
import com.gautam.bank.enums.LoanStatus;
import com.gautam.bank.enums.LoanType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder 
public class Loan extends BaseEntity {


    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_number", unique = true, nullable = false)
    private String loanNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type", nullable = false)
    private LoanType loanType;

    @Column(name = "loan_amount", precision = 18, scale = 2, nullable = false)
    private BigDecimal loanAmount;

    @Column(name = "interest_rate", precision = 5, scale = 2)
    private BigDecimal interestRate;

    @Column(name = "tenure_months")
    private Integer tenureMonths;

    @Column(name = "emi_amount", precision = 18, scale = 2)
    private BigDecimal emiAmount;

    @Column(name = "approved_amount", precision = 18, scale = 2)
    private BigDecimal approvedAmount;

    @Column(name = "disbursed_amount", precision = 18, scale = 2)
    private BigDecimal disbursedAmount;

    @Column(name = "remaining_amount", precision = 18, scale = 2)
    private BigDecimal remainingAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @Column(length = 500)
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private Employee approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "disbursed_at")
    private LocalDateTime disbursedAt;
}