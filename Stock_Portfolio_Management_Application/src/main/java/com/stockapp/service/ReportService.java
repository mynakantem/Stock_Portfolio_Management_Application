package com.stockapp.service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ReportService {
    byte[] generateExcelReport(Long portfolioId);
}
