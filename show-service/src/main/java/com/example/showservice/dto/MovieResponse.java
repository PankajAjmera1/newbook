package com.example.showservice.dto;


import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private Long movieId;
    private String movieName;
    private String language;
    private String genre;
    private String description;
    private LocalDate releaseDate;
    private Integer durationMinutes;
}