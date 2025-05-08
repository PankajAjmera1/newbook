package com.example.theaterservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieResponse {
    private Long movieId;
    private String movieName;
    private Integer durationMinutes;
    private String language;
    private String genre;
    private String description;
    private LocalDate releaseDate;

}