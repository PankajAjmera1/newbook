package com.example.showservice.feign;


import com.example.showservice.dto.TheaterResponse;
import org.springframework.cloud.openfeign.*;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "theater-service")
public interface TheaterFeignClient {
    @GetMapping("/api/theaters/{id}")
    TheaterResponse getTheaterById(@PathVariable Long id);
}