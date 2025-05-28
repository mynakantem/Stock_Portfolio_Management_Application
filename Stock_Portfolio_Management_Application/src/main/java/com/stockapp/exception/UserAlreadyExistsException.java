package com.stockapp.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super(username);
    }
}
