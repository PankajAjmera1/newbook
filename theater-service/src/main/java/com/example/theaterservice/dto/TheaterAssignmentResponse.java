package com.example.theaterservice.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TheaterAssignmentResponse {
    private Long theaterId;
    private String theaterName;
    private boolean success;
    private String message;
}