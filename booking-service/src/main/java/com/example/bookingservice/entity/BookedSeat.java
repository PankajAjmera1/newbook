package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookedSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;
    private SeatType seatType;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Booking booking;
}
