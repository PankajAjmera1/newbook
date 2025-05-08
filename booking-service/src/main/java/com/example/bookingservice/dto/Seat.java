package com.example.bookingservice.dto;

import com.example.bookingservice.entity.SeatType;
import lombok.Data;

@Data
public class Seat {
    private String seatNumber;
    private SeatType seatType;
    private Double price;
    private boolean booked;
}