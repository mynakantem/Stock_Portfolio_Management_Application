package com.stockapp.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApiException {
    public UserAlreadyExistsException(String username) {
        super("User already exists: " + username, "USER_EXISTS", HttpStatus.CONFLICT);
    }
}

