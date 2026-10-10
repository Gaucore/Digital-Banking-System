package com.gautam.bank.entity.beneficiary;

import com.gautam.bank.entity.BaseEntity;
import com.gautam.bank.entity.account.Account;
import com.gautam.bank.entity.customer.Customer;

import jakarta.persistence.Column;
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
import jakarta.persistence.*;
import lombok.*;


@Entity 
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@Table (name="beneficiaries")
public class Beneficiary extends  BaseEntity{

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false,unique = true,length = 20)
    private String beneficiaryCode;

    @Column(nullable = false,length = 100) 
    private String nickName;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "customer_id",nullable = false)
    private Customer customer;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "account_id",nullable = false)
    private Account account;
    
}
