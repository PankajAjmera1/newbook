package com.example.movieservice.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(name = "ErrorResponse", description = "Error response info")
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(description = "API path where error occurred")
    private String apiPath;

    @Schema(description = "HTTP status code")
    private HttpStatus errorCode;

    @Schema(description = "Error message")
    private String errorMessage;

    @Schema(description = "Error timestamp")
    private LocalDateTime errorTime;
}
