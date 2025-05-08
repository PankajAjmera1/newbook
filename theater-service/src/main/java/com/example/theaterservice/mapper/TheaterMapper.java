package com.example.theaterservice.mapper;


import com.example.theaterservice.dto.*;
import com.example.theaterservice.entity.Theater;

public class TheaterMapper {
    public static Theater mapToTheater(TheaterRequest request) {
        return Theater.builder()
                .name(request.getName())
                .location(request.getLocation())
                .capacity(request.getCapacity())
                .build();
    }

    public static TheaterResponse mapToTheaterResponse(Theater theater) {
        return TheaterResponse.builder()
                .theaterId(theater.getTheaterId())
                .name(theater.getName())
                .location(theater.getLocation())
                .capacity(theater.getCapacity())
                .build();
    }

    public static TheaterWithMovieResponse mapToTheaterWithMovieResponse(
            Theater theater , MovieResponse movie) {
        return TheaterWithMovieResponse.builder()
                .theaterId(theater.getTheaterId())
                .name(theater.getName())
                .location(theater.getLocation())
                .capacity(theater.getCapacity())
                .currentMovieId(theater.getCurrentMovieId())
                .movieName(movie.getMovieName())
                .description(movie.getDescription())
                .language(movie.getLanguage())
                .genre(movie.getGenre())
                .durationMinutes(movie.getDurationMinutes())
                .build();
    }

    public static TheaterAssignmentResponse mapToAssignmentResponse(
            Theater theater, boolean success, String message) {
        return TheaterAssignmentResponse.builder()
                .theaterId(theater.getTheaterId())
                .theaterName(theater.getName())
                .success(success)
                .message(message)
                .build();
    }
}
