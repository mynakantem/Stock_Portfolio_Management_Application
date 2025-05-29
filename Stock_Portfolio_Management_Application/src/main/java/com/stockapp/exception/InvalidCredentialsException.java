package com.stockapp.exception;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when the user enters wrong username or password.
 * It extends from ApiException so it returns a clean and consistent JSON error response.
 */
public class InvalidCredentialsException extends ApiException {

    // Constructor takes the username and forms an error message
    public InvalidCredentialsException(String username) {
        super(
            "Invalid credentials for user: " + username, 
            "INVALID_CREDENTIALS",                        
            HttpStatus.UNAUTHORIZED                       // Sends 401 status code
        );
    }
}
