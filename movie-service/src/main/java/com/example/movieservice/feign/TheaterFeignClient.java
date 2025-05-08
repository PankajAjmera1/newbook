package com.example.movieservice.feign;


import com.example.movieservice.dto.TheaterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "theater-service", url = "http://localhost:8082")
public interface TheaterFeignClient {

    @GetMapping("/api/theaters/{theaterId}/exists")
    boolean theaterExists(@PathVariable Long theaterId);

    @GetMapping("/api/theaters/{theaterId}")
    TheaterResponse getTheaterById(@PathVariable Long theaterId);
}