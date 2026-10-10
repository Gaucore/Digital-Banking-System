package com.gautam.bank.util.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.gautam.bank.dto.response.statement.StatementResponse;

@Component
public class StatementExcelGenerator {

    public byte[] generateExcel(
            String customerName,
            String accountNumber,
            String period,
            List<StatementResponse> statements) {

        try (XSSFWorkbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Bank Statement");

            // ==========================
            // Title Style
            // ==========================

            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);

            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setFont(titleFont);

            // ==========================
            // Header Style
            // ==========================

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());

            headerStyle.setFont(headerFont);

            // ==========================
            // Title
            // ==========================

            Row titleRow = sheet.createRow(0);

            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("GAUTAM DIGITAL BANK");
            titleCell.setCellStyle(titleStyle);

            // ==========================
            // Customer Details
            // ==========================

            Row row1 = sheet.createRow(2);
            row1.createCell(0).setCellValue("Customer");
            row1.createCell(1).setCellValue(customerName);

            Row row2 = sheet.createRow(3);
            row2.createCell(0).setCellValue("Account Number");
            row2.createCell(1).setCellValue(accountNumber);

            Row row3 = sheet.createRow(4);
            row3.createCell(0).setCellValue("Statement Period");
            row3.createCell(1).setCellValue(period);

            // ==========================
            // Table Header
            // ==========================

            Row headerRow = sheet.createRow(6);

            String[] headers = {
                    "Transaction No",
                    "Type",
                    "From Account",
                    "To Account",
                    "Amount",
                    "Previous Balance",
                    "Current Balance",
                    "Remarks",
                    "Transaction Date"
            };

            for (int i = 0; i < headers.length; i++) {

                Cell cell = headerRow.createCell(i);

                cell.setCellValue(headers[i]);

                cell.setCellStyle(headerStyle);
            }

            // ==========================
            // Data
            // ==========================

            int rowNum = 7;

            for (StatementResponse statement : statements) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(statement.getTransactionNumber());

                row.createCell(1).setCellValue(statement.getTransactionType().name());

                row.createCell(2).setCellValue(
                        statement.getFromAccountNumber() == null
                                ? ""
                                : statement.getFromAccountNumber());

                row.createCell(3).setCellValue(
                        statement.getToAccountNumber() == null
                                ? ""
                                : statement.getToAccountNumber());

                row.createCell(4).setCellValue(statement.getAmount().doubleValue());

                row.createCell(5).setCellValue(statement.getPreviousBalance().doubleValue());

                row.createCell(6).setCellValue(statement.getCurrentBalance().doubleValue());

                row.createCell(7).setCellValue(
                        statement.getRemarks() == null
                                ? ""
                                : statement.getRemarks());

                row.createCell(8).setCellValue(statement.getTransactionDate().toString());
            }

            // ==========================
            // Auto Size Columns
            // ==========================

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);

            System.out.println("Excel Size : " + outputStream.size());

            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error generating Excel", e);
        }
    }
}