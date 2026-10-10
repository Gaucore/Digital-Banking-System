package com.gautam.bank.controller.customer;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gautam.bank.dto.request.beneficiary.BeneficiaryRequest;
import com.gautam.bank.dto.request.beneficiary.BeneficiaryTransferRequest;
import com.gautam.bank.dto.request.beneficiary.UpdateBeneficiaryRequest;
import com.gautam.bank.dto.response.beneficiary.BeneficiaryResponse;
import com.gautam.bank.dto.response.common.ApiResponse;
import com.gautam.bank.dto.response.statement.StatementResponse;
import com.gautam.bank.dto.response.transaction.TransactionResponse;
import com.gautam.bank.service.BeneficiaryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/customer/beneficiaries")
@RequiredArgsConstructor
@Validated
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    @PostMapping
    public ResponseEntity<BeneficiaryResponse> addBeneficiary(@Valid @RequestBody BeneficiaryRequest request) {
        BeneficiaryResponse response = beneficiaryService.addBeneficiary(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BeneficiaryResponse>> getMyBeneficiaries() {
        List<BeneficiaryResponse> response = beneficiaryService.getMyBeneficiaries();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{beneficiaryCode}")
    public ResponseEntity<BeneficiaryResponse> updateBeneficiary(@PathVariable String beneficiaryCode,
            @Valid @RequestBody UpdateBeneficiaryRequest request) {
        BeneficiaryResponse response = beneficiaryService.updateBeneficiary(beneficiaryCode, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{beneficiaryCode}")
    public ResponseEntity<ApiResponse> deleteBeneficiary(@PathVariable String beneficiaryCode) {
        ApiResponse response = beneficiaryService.deleteBeneficiary(beneficiaryCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transferToBenficiary(
            @Valid @RequestBody BeneficiaryTransferRequest request) {
        TransactionResponse response = beneficiaryService.transferToBeneficiary(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // @GetMapping("/mini-statement/{accountNumber}")
    // public ResponseEntity<List<StatementResponse>> getMiniStatment(@PathVariable String accountNumber) {
    //     List<StatementResponse> response = beneficiaryService.getMiniStatement(accountNumber);
    //     return ResponseEntity.ok(response);
    // }

}
