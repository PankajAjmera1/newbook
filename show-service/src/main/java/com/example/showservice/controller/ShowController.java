package com.example.showservice.controller;


import com.example.showservice.dto.SeatResponse;
import com.example.showservice.dto.ShowRequest;
import com.example.showservice.dto.ShowResponse;
import com.example.showservice.service.IShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final IShowService showService;

    @PostMapping
    public ResponseEntity<ShowResponse> createShow(@RequestBody ShowRequest request) {
        ShowResponse response = showService.createShow(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowResponse> getShowDetails(@PathVariable Long id) {
        ShowResponse response = showService.getShowById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<ShowResponse>> getShowsByTheater(@PathVariable Long theaterId) {
        List<ShowResponse> responses = showService.getShowsByTheater(theaterId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowResponse>> getShowsByMovie(@PathVariable Long movieId) {
        List<ShowResponse> responses = showService.getShowsByMovie(movieId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<ShowResponse>> getUpcomingShows(
            @RequestParam(defaultValue = "7") int daysAhead) {
        if (daysAhead <= 0) {
            throw new IllegalArgumentException("Days ahead must be positive");
        }
        List<ShowResponse> responses = showService.getUpcomingShows(daysAhead);
        return ResponseEntity.ok(responses);
    }


    @PostMapping("/{showId}/book-seats")
    public ResponseEntity<Void> bookSeats(
            @PathVariable Long showId,
            @RequestBody List<String> seatNumbers) {
        showService.bookSeats(showId, seatNumbers);
        return ResponseEntity.ok().build();
    }



    // Add these new endpoints to support booking service operations
//    @PostMapping("/{showId}/lock-seats")
//    public ResponseEntity<List<SeatResponse>> lockSeats(
//            @PathVariable Long showId,
//            @RequestBody List<String> seatNumbers) {
//        List<SeatResponse> responses = showService.lockSeats(showId, seatNumbers);
//        return ResponseEntity.ok(responses);
//    }
//
//    @PostMapping("/{showId}/confirm-seats")
//    public ResponseEntity<Void> confirmSeats(
//            @PathVariable Long showId,
//            @RequestBody List<String> seatNumbers) {
//        showService.confirmSeats(showId, seatNumbers);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/{showId}/release-seats")
//    public ResponseEntity<Void> releaseSeats(
//            @PathVariable Long showId,
//            @RequestBody List<String> seatNumbers) {
//        showService.releaseSeats(showId, seatNumbers);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/{showId}/seats")
//    public ResponseEntity<List<SeatResponse>> getSeatsForShow(
//            @PathVariable Long showId,
//            @RequestParam(required = false) Boolean available) {
//        List<SeatResponse> responses = showService.getSeatsForShow(showId, available);
//        return ResponseEntity.ok(responses);
//    }
}
