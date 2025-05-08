package com.example.bookingservice.dto;

import com.example.bookingservice.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private Long showId;
    private String theaterName;
    private String movieName;
    private LocalDateTime showTime;
    private List<BookedSeatResponse> seats;
    private Double totalAmount;
    private BookingStatus status;
    private LocalDateTime bookingTime;
}
