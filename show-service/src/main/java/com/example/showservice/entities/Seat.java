package com.example.showservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(indexes = {
        @Index(name = "idx_seat_show_seatnumber", columnList = "show_id,seatNumber"),
        @Index(name = "idx_seat_show_booked", columnList = "show_id,isBooked")
})
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private String seatNumber;

    @Enumerated(EnumType.STRING) // Add this annotation
    private SeatType type;

    private Double price;
    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
}