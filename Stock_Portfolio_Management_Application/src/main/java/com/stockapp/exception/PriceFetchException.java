package com.stockapp.exception;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when the system fails to fetch stock prices from a third-party API.
 * Extending ApiException helps return a structured error response in JSON format.
 */

public class PriceFetchException extends ApiException {
    // Constructor that accepts a custom message when price fetching fails
	public PriceFetchException(String message) {
        super(
            message,                     
            "PRICE_FETCH_FAILED",       
            HttpStatus.BAD_GATEWAY      // 502 Bad Gateway indicates external API failure
        );
    }
}
