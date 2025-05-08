package com.example.theaterservice.service;


import com.example.theaterservice.dto.*;

import java.util.List;

public interface ITheaterService {
    TheaterResponse createTheater(TheaterRequest theaterRequest);
    TheaterResponse getTheaterById(Long id);
    List<TheaterResponse> getAllTheaters();
    boolean theaterExists(Long theaterId);
    TheaterWithMovieResponse assignMovieToTheater(Long theaterId, Long movieId);
    List<TheaterWithMovieResponse> getTheatersByMovie(Long movieId);


    List<TheaterAssignmentResponse> assignMovieToMultipleTheaters(BatchAssignmentRequest request);
    TheaterWithMovieResponse updateMovieInTheater(Long theaterId, Long newMovieId);
}