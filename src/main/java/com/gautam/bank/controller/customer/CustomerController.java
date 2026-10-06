package com.gautam.bank.controller.customer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gautam.bank.dto.request.customer.CustomerRequest;
import com.gautam.bank.dto.request.enableBankingCustomer.EnableInternetBankingRequest;
import com.gautam.bank.dto.response.account.CustomerAccountResponse;
import com.gautam.bank.dto.response.customer.CustomerResponse;
import com.gautam.bank.dto.response.customerProfile.CustomerProfileResponse;
import com.gautam.bank.dto.response.enableBankingCustomer.InternetBankingResponse;
import com.gautam.bank.dto.response.transaction.CustomerTransactionResponse;
import com.gautam.bank.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController 
@RequestMapping ("/api/customers")
@RequiredArgsConstructor 
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse response =customerService.createCustomer(request);        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/enable-internet-banking")
    public ResponseEntity<InternetBankingResponse> enableInternetBanking(@Valid @RequestBody EnableInternetBankingRequest request) {
        InternetBankingResponse response=customerService.enableInternetBanking(request);        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<CustomerProfileResponse> getMyProfile(){
        CustomerProfileResponse response=customerService.getMyProfile();
        return ResponseEntity.ok(response);
    } 

    @GetMapping("/accounts")
    public ResponseEntity<List<CustomerAccountResponse>> getMyAccounts() {
        List<CustomerAccountResponse> response= customerService.getMyAccounts();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/transactions")
    public ResponseEntity<List<CustomerTransactionResponse>> getMyTransactions() {
        List<CustomerTransactionResponse> response= customerService.getMyTransactions();
        return ResponseEntity.ok(response);
    }
    
    
}
