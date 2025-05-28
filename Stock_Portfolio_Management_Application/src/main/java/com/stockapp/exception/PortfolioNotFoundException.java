package com.stockapp.exception;

import org.springframework.http.HttpStatus;

public class PortfolioNotFoundException extends ApiException {
    public PortfolioNotFoundException(Long id) {
        super("Portfolio with ID " + id + " not found", "PORTFOLIO_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
