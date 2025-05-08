package com.example.movieservice.service.impl;

import com.example.movieservice.dto.MovieRequest;
import com.example.movieservice.dto.MovieResponse;
import com.example.movieservice.entity.Movie;
import com.example.movieservice.exception.MovieNotFoundException;
import com.example.movieservice.feign.TheaterFeignClient;
import com.example.movieservice.mapper.MovieMapper;
import com.example.movieservice.repository.MovieRepository;
import com.example.movieservice.service.IMovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements IMovieService {

    private final MovieRepository movieRepository;
    private final TheaterFeignClient theaterFeignClient;

//    @Override
//    public MovieResponse createMovie(MovieRequest movieRequest) {
//        // First check if theater exists
//        if (!theaterFeignClient.theaterExists(movieRequest.getTheaterId())) {
//            throw new TheaterNotFoundException("Theater not found with id: " + movieRequest.getTheaterId());
//        }
//
//        Movie movie = MovieMapper.mapToMovie(movieRequest);
//        Movie savedMovie = movieRepository.save(movie);
//        return MovieMapper.mapToMovieResponse(savedMovie);
//    }
//
//
//
//
//    @Override
//    public MovieWithTheaterResponse getMovieWithTheaterInfo(Long id) {
//        Movie movie = movieRepository.findById(id)
//                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
//
//        TheaterResponse theaterResponse = null;
//        if (movie.getTheaterId() != null) {
//            theaterResponse = theaterFeignClient.getTheaterById(movie.getTheaterId());
//        }
//
//        return MovieMapper.mapToMovieWithTheaterResponse(movie, theaterResponse);
//    }
//
//
//    @Override
//    public MovieResponse getMovieById(Long id) {
//        Movie movie = movieRepository.findById(id)
//                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
//        return MovieMapper.mapToMovieResponse(movie);
//    }


    @Override
    public MovieResponse createMovie(MovieRequest request) {
        Movie movie = MovieMapper.mapToMovie(request);
        return MovieMapper.mapToMovieResponse(movieRepository.save(movie));
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
        return MovieMapper.mapToMovieResponse(movie);
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(MovieMapper::mapToMovieResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MovieResponse updateMovie(Long id, MovieRequest movieRequest) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));

        existingMovie.setMovieName(movieRequest.getMovieName());
        existingMovie.setLanguage(movieRequest.getLanguage());
        existingMovie.setGenre(movieRequest.getGenre());
        existingMovie.setDescription(movieRequest.getDescription());
        existingMovie.setReleaseDate(movieRequest.getReleaseDate());

        Movie updatedMovie = movieRepository.save(existingMovie);
        return MovieMapper.mapToMovieResponse(updatedMovie);
    }

    @Override
    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
        movieRepository.delete(movie);
    }

    @Override
    public List<MovieResponse> searchMovies(String query) {
        List<Movie> movies = movieRepository.findByMovieNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
        return movies.stream()
                .map(MovieMapper::mapToMovieResponse)
                .collect(Collectors.toList());
    }
}