
package com.gautam.bank.util.pdf;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.print.attribute.standard.PageRanges;

import org.springframework.stereotype.Component;

import com.gautam.bank.dto.response.statement.StatementResponse;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component
public class StatementPdfGenerator {

    public byte[] generatePdf(String customerName, String accountNumber, String period,
            List<StatementResponse> statements) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {

            Document document = new Document();

            PdfWriter.getInstance(document, output);

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("GAUTAM DIGITAL BANK", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            Paragraph heading = new Paragraph("Account STATEMENT");
            heading.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(heading);
            document.add(new Paragraph(""));

            document.add(new Paragraph("Customer : " + customerName));
            document.add(new Paragraph("Account Number : " + accountNumber));
            document.add(new Paragraph("Statement Period : " + period));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);

            table.setWidthPercentage(100);

            table.addCell("Txn No");

            table.addCell("Type");

            table.addCell("Amount");

            table.addCell("Balance");

            table.addCell("Date");

            for (StatementResponse statement : statements) {
                table.addCell(statement.getTransactionNumber());
                table.addCell(statement.getTransactionType().name());
                table.addCell(statement.getAmount().toString());
                table.addCell(statement.getCurrentBalance().toString());
                table.addCell(statement.getTransactionDate().toString());
            }

            document.add(table);

            BigDecimal totalCredit = BigDecimal.ZERO;
            BigDecimal totalDebit = BigDecimal.ZERO;
            for (StatementResponse statement : statements) {
                switch (statement.getTransactionType()) {

                    case DEPOSIT:
                        totalCredit = totalCredit.add(statement.getAmount());
                        break;

                    case WITHDRAW:
                    case TRANSFER:
                        totalDebit = totalDebit.add(statement.getAmount());
                        break;

                    default:
                        break;
                }
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total Credit : " + totalCredit));
            document.add(new Paragraph("Total Debit : " + totalDebit));

            if (!statements.isEmpty()) {
                document.add(new Paragraph("Closing Balance : " + statements.getLast().getCurrentBalance()));
            }

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }

        System.out.println("PDF Size : " + output.size());

        return output.toByteArray();
    }
}