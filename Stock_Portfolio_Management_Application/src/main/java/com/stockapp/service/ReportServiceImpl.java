package com.stockapp.service;

import com.stockapp.model.Holding;
import com.stockapp.repository.HoldingRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final HoldingRepository holdingRepository;

    // Constructor injection to get HoldingRepository from Spring
    public ReportServiceImpl(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }

    // This method creates an Excel report of all holdings in a portfolio
    @Override
    public byte[] generateExcelReport(Long portfolioId) {
        logger.info("Starting Excel generation for portfolio ID: {}", portfolioId);

        // Fetch holdings from the database using portfolio ID
        List<Holding> holdings = holdingRepository.findByPortfolioId(portfolioId);

        if (holdings == null || holdings.isEmpty()) {
            logger.warn("No holdings found for portfolio ID: {}", portfolioId);
        }

        try (
            Workbook workbook = new XSSFWorkbook(); // Creates an Excel workbook
            ByteArrayOutputStream out = new ByteArrayOutputStream() // Used to store the Excel file in memory
        ) {
            Sheet sheet = workbook.createSheet("Portfolio Report"); // Sheet name

            // Create header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Symbol");
            header.createCell(1).setCellValue("Quantity");
            header.createCell(2).setCellValue("Buy Price");
            header.createCell(3).setCellValue("Total Value");

            // Fill data rows
            int rowNum = 1;
            for (Holding h : holdings) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(h.getSymbol());
                row.createCell(1).setCellValue(h.getQuantity());
                row.createCell(2).setCellValue(h.getBuyPrice());
                row.createCell(3).setCellValue(h.getQuantity() * h.getBuyPrice());
            }

            workbook.write(out); // Write workbook to memory stream
            logger.info("Excel report generated successfully for portfolio ID: {}", portfolioId);
            return out.toByteArray(); // Return byte array to controller

        } catch (IOException e) {
            logger.error("Error generating Excel report for portfolio ID: {}", portfolioId, e);
            throw new RuntimeException("Excel report generation failed", e);
        }
    }
}
