package com.gautam.bank.service;

import java.util.List;

import com.gautam.bank.dto.request.statement.MonthlyStatementRequest;
import com.gautam.bank.dto.request.statement.StatementRequest;
import com.gautam.bank.dto.response.statement.StatementResponse;

public interface StatementService {

    List<StatementResponse> getMiniStatement(String accountNumber);

    List<StatementResponse> getStatement(StatementRequest request);

    List<StatementResponse> getMonthlyStatement(MonthlyStatementRequest request);

    byte[] downloadStatementPdf(StatementRequest request);

    byte[] downloadStatementExcel(StatementRequest request);
}