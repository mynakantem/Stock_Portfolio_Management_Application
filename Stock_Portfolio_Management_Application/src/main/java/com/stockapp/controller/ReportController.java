package com.stockapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.stockapp.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
	
	 private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
	 
	    @Autowired
	    private ReportService reportService;

	    @GetMapping("/export/excel")
	    public ResponseEntity<byte[]> exportExcelReport(@RequestParam Long portfolioId) {
	    	logger.info("Received request to export Excel report for portfolio ID: {}", portfolioId);
	        byte[] data = reportService.generateExcelReport(portfolioId);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	        headers.setContentDisposition(ContentDisposition.attachment().filename("portfolio_report.xlsx").build());

	        logger.info("Excel report successfully generated for portfolio ID: {}", portfolioId);
	        return new ResponseEntity<>(data, headers, HttpStatus.OK);
	    }
	}

