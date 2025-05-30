package com.stockapp.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(String username) {
        super("Username not found: " + username, "USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}

