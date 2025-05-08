package com.example.theaterservice.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BatchAssignmentRequest {
    @NotNull
    private Long movieId;

    @NotNull
    private List<Long> theaterIds;
}