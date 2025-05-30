package com.stockapp.exception;

import org.springframework.http.HttpStatus;

public class PriceFetchException extends ApiException {
    public PriceFetchException(String message) {
        super(message, "PRICE_FETCH_FAILED", HttpStatus.BAD_GATEWAY);
    }
}
