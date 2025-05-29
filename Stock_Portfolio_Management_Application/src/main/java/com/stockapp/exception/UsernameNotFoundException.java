package com.stockapp.exception;

import org.springframework.http.HttpStatus;

public class UsernameNotFoundException extends RuntimeException {
	//username not found exception throws if user is not found

    public UsernameNotFoundException(String username) {
        super(username);
}
}
