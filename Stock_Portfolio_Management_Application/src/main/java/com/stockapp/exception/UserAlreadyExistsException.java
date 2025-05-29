package com.stockapp.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RuntimeException {
	//user already exists exception throws if user is already exists
    public UserAlreadyExistsException(String username) {
        super(username);
    }
}
