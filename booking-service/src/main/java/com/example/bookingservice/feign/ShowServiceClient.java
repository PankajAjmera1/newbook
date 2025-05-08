//package com.example.bookingservice.feign;
//
//import com.example.bookingservice.dto.Seat;
//import com.example.bookingservice.dto.ShowDetails;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.List;
//
//@FeignClient(name = "show-service", url = "${show.service.url}")
//public interface ShowServiceClient {
//
//    @GetMapping("/api/shows/{showId}/details")
//    ShowDetails getShowDetails(@PathVariable Long showId);
//
//    @PostMapping("/api/shows/{showId}/lock-seats")
//    List<Seat> lockSeats(
//            @PathVariable Long showId,
//            @RequestBody List<String> seatNumbers);
//
//    @PostMapping("/api/shows/{showId}/confirm-seats")
//    void confirmSeats(
//            @PathVariable Long showId,
//            @RequestBody List<String> seatNumbers);
//}

package  com.example.bookingservice.feign;

import com.example.bookingservice.dto.Seat;
import com.example.bookingservice.dto.ShowDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "show-service", url = "${show.service.url}")
public interface ShowServiceClient {

    @GetMapping("/api/shows/{id}")
    ShowDetails getShowDetails(@PathVariable Long id);

    @PostMapping("/api/shows/{showId}/lock-seats")
    List<Seat> lockSeats(
            @PathVariable Long showId,
            @RequestBody List<String> seatNumbers);

    @PostMapping("/api/shows/{showId}/confirm-seats")
    void confirmSeats(
            @PathVariable Long showId,
            @RequestBody List<String> seatNumbers);

    @PostMapping("/api/shows/{showId}/release-seats")
    void releaseSeats(
            @PathVariable Long showId,
            @RequestBody List<String> seatNumbers);
}