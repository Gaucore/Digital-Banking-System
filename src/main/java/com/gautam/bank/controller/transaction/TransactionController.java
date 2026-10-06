package com.gautam.bank.controller.transaction;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gautam.bank.dto.request.transaction.DepositRequest;
import com.gautam.bank.dto.request.transaction.TransferRequest;
import com.gautam.bank.dto.request.transaction.WithdrawRequest;
import com.gautam.bank.dto.response.transaction.TransactionResponse;
import com.gautam.bank.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/api/transactions") 
@RequiredArgsConstructor 
public class TransactionController {
    
    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody DepositRequest request) {
        TransactionResponse response = transactionService.deposit(request);        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody WithdrawRequest request) {
        TransactionResponse response =transactionService.withdraw(request);        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
        TransactionResponse response=transactionService.transfer(request);        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
}
