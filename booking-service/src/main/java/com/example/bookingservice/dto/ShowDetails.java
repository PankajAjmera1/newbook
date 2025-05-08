package com.example.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowDetails {
    private Long id;
    private String theaterName;
    private Long theaterId;
    private String movieName;
    private Long movieId;
    private LocalDateTime startTime;
}