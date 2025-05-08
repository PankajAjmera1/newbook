package com.example.movieservice.controller;


import com.example.movieservice.dto.MovieRequest;
import com.example.movieservice.dto.MovieResponse;
import com.example.movieservice.service.IMovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api/movies")
//@AllArgsConstructor
//public class MovieController {
//
//    private final IMovieService movieService;
//
//    @PostMapping("/create")
//    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody MovieRequest movieRequest) {
//        MovieResponse response = movieService.createMovie(movieRequest);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
//        MovieResponse response = movieService.getMovieById(id);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/{id}/with-theater")
//    public ResponseEntity<MovieWithTheaterResponse> getMovieWithTheaterInfo(@PathVariable Long id) {
//        MovieWithTheaterResponse response = movieService.getMovieWithTheaterInfo(id);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<MovieResponse>> getAllMovies() {
//        List<MovieResponse> responses = movieService.getAllMovies();
//        return ResponseEntity.ok(responses);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<MovieResponse> updateMovie(
//            @PathVariable Long id,
//            @Valid @RequestBody MovieRequest movieRequest) {
//        MovieResponse response = movieService.updateMovie(id, movieRequest);
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
//        movieService.deleteMovie(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<List<MovieResponse>> searchMovies(
//            @RequestParam String query) {
//        List<MovieResponse> responses = movieService.searchMovies(query);
//        return ResponseEntity.ok(responses);
//    }
//}

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
public class MovieController {
    private final IMovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody MovieRequest request) {
        return new ResponseEntity<>(movieService.createMovie(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieRequest request) {
        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> searchMovies(@RequestParam String query) {
        return ResponseEntity.ok(movieService.searchMovies(query));
    }
}