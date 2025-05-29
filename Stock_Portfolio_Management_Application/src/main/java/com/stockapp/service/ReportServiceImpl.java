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

    //constructor based injection
    public ReportServiceImpl(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }

    @Override
    public byte[] generateExcelReport(Long portfolioId) {
        logger.info("Starting Excel generation for portfolio ID: {}", portfolioId);
        List<Holding> holdings = holdingRepository.findByPortfolioId(portfolioId);

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Portfolio Report");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Symbol");
            header.createCell(1).setCellValue("Quantity");
            header.createCell(2).setCellValue("Buy Price");
            header.createCell(3).setCellValue("Total Value");

            // Data
            int rowNum = 1;
            for (Holding h : holdings) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(h.getSymbol());
                row.createCell(1).setCellValue(h.getQuantity());
                row.createCell(2).setCellValue(h.getBuyPrice());
                row.createCell(3).setCellValue(h.getQuantity() * h.getBuyPrice());
            }

            workbook.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            logger.error("Error generating Excel report", e);
            throw new RuntimeException("Excel report generation failed", e);
        }
    }
}
