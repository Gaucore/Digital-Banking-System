package com.gautam.bank.controller.customer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.gautam.bank.dto.request.statement.MonthlyStatementRequest;
import com.gautam.bank.dto.request.statement.StatementRequest;
import com.gautam.bank.dto.response.statement.StatementResponse;
import com.gautam.bank.service.StatementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/customer/statements")
@RequiredArgsConstructor
@Validated
public class StatementController {

    private final StatementService statementService;

    @GetMapping("/mini/{accountNumber}")
    public ResponseEntity<List<StatementResponse>> getMiniStatement(@PathVariable String accountNumber) {
        return ResponseEntity.ok(statementService.getMiniStatement(accountNumber));
    }

    @PostMapping("/date-range")
    public ResponseEntity<List<StatementResponse>> getStatement(@Valid @RequestBody StatementRequest request) {
        return ResponseEntity.ok(statementService.getStatement(request));
    }

    @PostMapping("/monthly")
    public ResponseEntity<List<StatementResponse>> getMonthlyStatement(
            @Valid @RequestBody MonthlyStatementRequest request) {
        return ResponseEntity.ok(statementService.getMonthlyStatement(request));
    }

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdf(@Valid @RequestBody StatementRequest request) {
        byte[] pdf = statementService.downloadStatementPdf(request);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statement.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(pdf);
    }

    @PostMapping("/excel")
    public ResponseEntity<byte[]> downloadExcel(@Valid @RequestBody StatementRequest request) {
        byte[] excel = statementService.downloadStatementExcel(request);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=statement.xlsx")
                .contentType(
                        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }

    

}