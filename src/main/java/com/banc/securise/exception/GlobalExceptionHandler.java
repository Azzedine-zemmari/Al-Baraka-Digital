package com.banc.securise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountInactiveException.class)
    public ResponseEntity<?> handleInactiveAccountException(AccountInactiveException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
