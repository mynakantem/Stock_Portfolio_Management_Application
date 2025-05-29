package com.stockapp.exception;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when a user tries to register with a username that already exists.
 * Extending ApiException ensures consistent error handling across the application.
 */

public class UserAlreadyExistsException extends ApiException {
	//user already exists exception throws if user is already exists
    public UserAlreadyExistsException(String username) {
    	super(
                "User already exists: " + username, 
                "USER_EXISTS",                      
                HttpStatus.CONFLICT                 // 409 Conflict means the username is already taken
            );
        }
    }






        