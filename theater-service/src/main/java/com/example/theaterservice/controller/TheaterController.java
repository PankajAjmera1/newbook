package com.example.theaterservice.controller;

import com.example.theaterservice.dto.*;
import com.example.theaterservice.service.ITheaterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
@RequiredArgsConstructor
public class TheaterController {

    private final ITheaterService theaterService;

    @PostMapping
    public ResponseEntity<TheaterResponse> createTheater(@Valid @RequestBody TheaterRequest theaterRequest) {
        TheaterResponse response = theaterService.createTheater(theaterRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheaterResponse> getTheaterById(@PathVariable Long id) {
        return ResponseEntity.ok(theaterService.getTheaterById(id));
    }

    @GetMapping
    public ResponseEntity<List<TheaterResponse>> getAllTheaters() {
        return ResponseEntity.ok(theaterService.getAllTheaters());
    }

    @GetMapping("/{theaterId}/exists")
    public ResponseEntity<Boolean> theaterExists(@PathVariable Long theaterId) {
        return ResponseEntity.ok(theaterService.theaterExists(theaterId));
    }


    @PostMapping("/{theaterId}/assign-movie/{movieId}")
    public ResponseEntity<TheaterWithMovieResponse> assignMovie(
            @PathVariable Long theaterId,
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                theaterService.assignMovieToTheater(theaterId, movieId));
    }

    @GetMapping("/by-movie/{movieId}")
    public ResponseEntity<List<TheaterWithMovieResponse>> getTheatersByMovie(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                theaterService.getTheatersByMovie(movieId));
    }

    @PostMapping("/batch-assign")
    public ResponseEntity<List<TheaterAssignmentResponse>> assignMovieToMultipleTheaters(
            @Valid @RequestBody BatchAssignmentRequest request) {
        return ResponseEntity.ok(
                theaterService.assignMovieToMultipleTheaters(request));
    }

    @PutMapping("/{theaterId}/movies/{newMovieId}")
    public ResponseEntity<TheaterWithMovieResponse> updateMovieInTheater(
            @PathVariable Long theaterId,
            @PathVariable Long newMovieId) {
        return ResponseEntity.ok(
                theaterService.updateMovieInTheater(theaterId, newMovieId));
    }
}