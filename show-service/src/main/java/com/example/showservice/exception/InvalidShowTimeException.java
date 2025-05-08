package com.example.showservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidShowTimeException extends RuntimeException {
    public InvalidShowTimeException(String message) {
        super(message);
    }
}