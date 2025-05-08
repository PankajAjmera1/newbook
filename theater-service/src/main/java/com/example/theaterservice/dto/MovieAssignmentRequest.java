package com.example.theaterservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieAssignmentRequest {
    @NotNull
    private Long movieId;


}