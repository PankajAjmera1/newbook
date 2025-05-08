package com.example.showservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ShowConflictException extends RuntimeException {
    public ShowConflictException(String message) {
        super(message);
    }
}