package com.stockapp.exception;

import org.springframework.http.HttpStatus;

public class FileGenerationException extends ApiException {
    public FileGenerationException(String message) {
    	super(message, "REPORT_GENERATION_FAILED", HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

