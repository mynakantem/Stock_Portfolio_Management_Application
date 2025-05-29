package com.stockapp.exception;

public class PriceFetchException extends RuntimeException {
	public PriceFetchException(String message) {
        super(message);
    }
}
