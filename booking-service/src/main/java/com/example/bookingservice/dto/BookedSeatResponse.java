package com.example.bookingservice.dto;

import com.example.bookingservice.entity.SeatType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookedSeatResponse {
    private String seatNumber;
    private SeatType seatType;
    private Double price;
}