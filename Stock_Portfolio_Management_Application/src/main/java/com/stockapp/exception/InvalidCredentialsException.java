package com.stockapp.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApiException {

    // Constructor takes the username and forms an error message
    public InvalidCredentialsException(String username) {
        super(
            "Invalid credentials for user: " + username, 
            "INVALID_CREDENTIALS",                        
            HttpStatus.UNAUTHORIZED                      
        );
    }
}
