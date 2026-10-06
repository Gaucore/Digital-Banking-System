package com.gautam.bank.controller.account;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gautam.bank.dto.request.account.AccountRequest;
import com.gautam.bank.dto.response.account.AccountResponse;
import com.gautam.bank.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/api/accounts") 
@RequiredArgsConstructor 
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> openAccount(@Valid @RequestBody AccountRequest request) {
        AccountResponse response =accountService.openAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    
}
