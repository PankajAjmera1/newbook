package com.example.theaterservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TheaterWithMovieResponse {
    private Long theaterId;
    private String name;
    private String location;
    private Integer capacity;
    private Long currentMovieId;
    private String movieName;
    private String language;
    private String genre;
    private String description;
    private LocalDateTime releaseDate;
    private Integer durationMinutes;

}