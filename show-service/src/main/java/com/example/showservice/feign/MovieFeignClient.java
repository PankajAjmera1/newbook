package com.example.showservice.feign;

import com.example.showservice.dto.MovieResponse;
import org.springframework.cloud.openfeign.*;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "movie-service")
public interface MovieFeignClient {
    @GetMapping("/api/movies/{id}")
    MovieResponse getMovieById(@PathVariable Long id);
}