package com.gautam.bank.dto.response.customer;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.gautam.bank.enums.CustomerStatus;
import com.gautam.bank.enums.Gender;

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
public class CustomerResponse {

    private Long id;
    private String customerCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String address;
    private CustomerStatus status;
    private LocalDateTime createdAt;

}
