package com.gautam.bank.dto.request.beneficiary;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UpdateBeneficiaryRequest {

    @NotBlank (message = "Nickname is required")
    @Size (max = 100,message = "Nickname cannot exceed 100 characters")
    private String nickName;
    
}
