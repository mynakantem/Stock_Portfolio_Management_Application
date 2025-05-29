package com.stockapp.ServiceTest;

import com.stockapp.model.Holding;
import com.stockapp.repository.HoldingRepository;
import com.stockapp.service.ReportServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReportServiceImplTest {

    private ReportServiceImpl reportService;
    private HoldingRepository holdingRepository;

    @BeforeEach
    public void setUp() {
        holdingRepository = mock(HoldingRepository.class);
        reportService = new ReportServiceImpl(holdingRepository); 
    }

    @Test
    public void testGenerateExcelReport_returnsBytes() {
        Holding h1 = new Holding();
        h1.setSymbol("AAPL");
        h1.setQuantity(10);
        h1.setBuyPrice(150.0);

        Holding h2 = new Holding();
        h2.setSymbol("TCS");
        h2.setQuantity(5);
        h2.setBuyPrice(3000.0);

        List<Holding> holdings = List.of(h1, h2);
        when(holdingRepository.findByPortfolioId(1L)).thenReturn(holdings);

        byte[] excelBytes = reportService.generateExcelReport(1L);

        assertNotNull(excelBytes);
        assertTrue(excelBytes.length > 0);
    }
}
