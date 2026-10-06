package com.gautam.bank.dto.response.customerProfile;

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
public class CustomerProfileResponse {

    private String customerCode;
    private String firstName;
    private String lastName;
    private String email;
    private  String phone;
    private String status;
    private String username;
    
}
