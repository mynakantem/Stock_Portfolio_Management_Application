package com.stockapp.exception;



import org.springframework.http.HttpStatus;

public class InvalidRoleException extends ApiException {
//if any other role is trying to access update enpoint rather than admin,this error will be thrown
    public InvalidRoleException(String message) {
        super(message, "ACCESS_DENIED", HttpStatus.FORBIDDEN);
    }
}
