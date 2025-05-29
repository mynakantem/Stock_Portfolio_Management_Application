package com.stockapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Inner static class to format all error responses
    public static class ErrorResponse {
        private final LocalDateTime timestamp;
        private final String message;
        private final String errorCode;
        private final int status;

        public ErrorResponse(String message, String errorCode, int status) {
            this.timestamp = LocalDateTime.now();
            this.message = message;
            this.errorCode = errorCode;
            this.status = status;
        }

        public LocalDateTime getTimestamp() { return timestamp; }
        public String getMessage() { return message; }
        public String getErrorCode() { return errorCode; }
        public int getStatus() { return status; }
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        ErrorResponse response = new ErrorResponse(
            ex.getMessage(), ex.getErrorCode(), ex.getStatus().value()
        );
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<Object> handleInvalidRoleException(InvalidRoleException ex) {
        return new ResponseEntity<>(Map.of(
            "message", ex.getMessage(),
            "errorCode", "INVALID_ROLE",
            "status", HttpStatus.FORBIDDEN.value()
        ), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse response = new ErrorResponse(
            "Something went wrong",
            "INTERNAL_ERROR",
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
