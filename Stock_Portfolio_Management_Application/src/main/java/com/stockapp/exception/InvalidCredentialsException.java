package com.stockapp.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
