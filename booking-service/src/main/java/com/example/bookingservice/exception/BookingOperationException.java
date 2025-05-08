package com.example.bookingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingOperationException extends RuntimeException {
    public BookingOperationException(String message) {
        super(message);
    }
}