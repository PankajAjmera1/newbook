package com.example.movieservice.mapper;

import com.example.movieservice.dto.MovieRequest;
import com.example.movieservice.dto.MovieResponse;
import com.example.movieservice.entity.Movie;

//public class MovieMapper {
//
//    public static Movie mapToMovie(MovieRequest movieRequest) {
//        return Movie.builder()
//                .movieName(movieRequest.getMovieName())
//                .language(movieRequest.getLanguage())
//                .genre(movieRequest.getGenre())
//                .description(movieRequest.getDescription())
//                .releaseDate(movieRequest.getReleaseDate())
//                .theaterId(movieRequest.getTheaterId())
//
//                .build();
//    }
//
//    public static MovieResponse mapToMovieResponse(Movie movie) {
//        return MovieResponse.builder()
//                .movieId(movie.getMovieId())
//                .movieName(movie.getMovieName())
//                .language(movie.getLanguage())
//                .genre(movie.getGenre())
//                .description(movie.getDescription())
//                .releaseDate(movie.getReleaseDate())
//                .build();
//    }
//
//
//    public static MovieWithTheaterResponse mapToMovieWithTheaterResponse(Movie movie, TheaterResponse theaterResponse) {
//        return MovieWithTheaterResponse.builder()
//                .movieId(movie.getMovieId())
//                .movieName(movie.getMovieName())
//                .language(movie.getLanguage())
//                .genre(movie.getGenre())
//                .description(movie.getDescription())
//                .releaseDate(movie.getReleaseDate())
//                .theaterResponse(theaterResponse)
//                .build();
//    }
//
//}

public class MovieMapper {
    public static Movie mapToMovie(MovieRequest request) {
        return Movie.builder()
                .movieName(request.getMovieName())
                .language(request.getLanguage())
                .genre(request.getGenre())
                .description(request.getDescription())
                .releaseDate(request.getReleaseDate())
                .durationMinutes(request.getDurationMinutes())
                .build();
    }

    public static MovieResponse mapToMovieResponse(Movie movie) {
        return MovieResponse.builder()
                .movieId(movie.getMovieId())
                .movieName(movie.getMovieName())
                .language(movie.getLanguage())
                .genre(movie.getGenre())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .durationMinutes(movie.getDurationMinutes())
                .build();
    }
}
