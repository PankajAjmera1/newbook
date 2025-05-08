package com.example.theaterservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheaterRequest {
    private String name;
    private String location;
    private Integer capacity;
}