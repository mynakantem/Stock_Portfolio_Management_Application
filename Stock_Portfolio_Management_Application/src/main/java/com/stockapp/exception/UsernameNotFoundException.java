package com.stockapp.exception;

import org.springframework.http.HttpStatus;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String username) {
        super(username);
}
}
