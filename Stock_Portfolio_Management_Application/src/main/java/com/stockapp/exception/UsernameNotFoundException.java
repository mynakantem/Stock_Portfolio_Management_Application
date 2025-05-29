package com.stockapp.exception;

import org.springframework.http.HttpStatus;

public class UsernameNotFoundException extends ApiException {
	//username not found exception throws if user is not found
	public UsernameNotFoundException(String username) {
        super("Username not found: " + username, "USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}

    