package com.stockapp.reportservicetest;


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
	    	
	        // Create a dummy version of HoldingRepository
	        holdingRepository = mock(HoldingRepository.class);

	        // Create ReportService and inject the dummy repository
	        reportService = new ReportServiceImpl();
	        reportService.setHoldingRepository(holdingRepository); // use a setter if needed
	    }

	    @Test
	    public void testGenerateExcelReport_returnsBytes() {
	        // Creating a sample list of holdings
	        Holding h1 = new Holding();
	        h1.setSymbol("AAPL");
	        h1.setQuantity(10);
	        h1.setBuyPrice(150.0);

	        Holding h2 = new Holding();
	        h2.setSymbol("TCS");
	        h2.setQuantity(5);
	        h2.setBuyPrice(3000.0);

	        List<Holding> holdings = List.of(h1, h2);

	        // When the method is called, return the sample list
	        when(holdingRepository.findByPortfolioId(1L)).thenReturn(holdings);

	        // Calling the actual method
	        byte[] excelBytes = reportService.generateExcelReport(1L);

	        // Check if it returned something
	        assertNotNull(excelBytes);
	        assertTrue(excelBytes.length > 0);
	    }
	}


	