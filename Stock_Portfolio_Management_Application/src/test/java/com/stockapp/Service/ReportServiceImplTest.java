package com.stockapp.Service;

import com.stockapp.model.Holding;
import com.stockapp.repository.HoldingRepository;
import com.stockapp.service.ReportServiceImpl;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceImplTest {

    private HoldingRepository holdingRepository;
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        holdingRepository = Mockito.mock(HoldingRepository.class);
        reportService = new ReportServiceImpl(holdingRepository);  
    }

    @Test
    void testGenerateExcelReport() throws Exception {
        // Mock data
        Holding h1 = new Holding();
        h1.setSymbol("TATASTEEL");
        h1.setQuantity(10);
        h1.setBuyPrice(100.0);

        Holding h2 = new Holding();
        h2.setSymbol("INFY");
        h2.setQuantity(5);
        h2.setBuyPrice(150.0);

        List<Holding> holdings = Arrays.asList(h1, h2);

        // Mock repository response
        when(holdingRepository.findByPortfolioId(1L)).thenReturn(holdings);

        // Call the method
        byte[] excelData = reportService.generateExcelReport(1L);

        // Validate that data is not null or empty
        assertNotNull(excelData);
        assertTrue(excelData.length > 0);

        // Optionally validate Excel structure
        try (var workbook = WorkbookFactory.create(new ByteArrayInputStream(excelData))) {
            var sheet = workbook.getSheetAt(0);
            assertEquals("Portfolio Report", sheet.getSheetName());
            assertEquals("Symbol", sheet.getRow(0).getCell(0).getStringCellValue());
        }
    }
}
