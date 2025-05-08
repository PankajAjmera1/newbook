package com.example.movieservice.service;

import com.example.movieservice.dto.MovieRequest;
import com.example.movieservice.dto.MovieResponse;

import java.util.List;

public interface IMovieService {
    MovieResponse createMovie(MovieRequest movieRequest);
    MovieResponse getMovieById(Long id);
    List<MovieResponse> getAllMovies();
    MovieResponse updateMovie(Long id, MovieRequest movieRequest);
    void deleteMovie(Long id);
    List<MovieResponse> searchMovies(String query);
//    MovieWithTheaterResponse getMovieWithTheaterInfo(Long id);
}