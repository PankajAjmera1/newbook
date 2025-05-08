package com.example.showservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSeatCountException extends RuntimeException {
    public InvalidSeatCountException(String message) {
        super(message);
    }
}