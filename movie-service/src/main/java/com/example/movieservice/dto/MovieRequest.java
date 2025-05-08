package com.example.movieservice.dto;


import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Builder
@Data
public class MovieRequest {
    @NotBlank(message = "Movie name is required")
    @Size(max = 100)
    private String movieName;

    @NotBlank(message = "Language is required")
    @Size(max = 50)
    private String language;

    @NotBlank(message = "Genre is required")
    @Size(max = 50)
    private String genre;

    @NotBlank(message = "Description is required")
    @Size(max = 500)
    private String description;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    @NotNull @Positive
    private Integer durationMinutes;


}

