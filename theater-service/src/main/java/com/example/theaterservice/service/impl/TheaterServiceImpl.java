package com.example.theaterservice.service.impl;


import com.example.theaterservice.dto.*;
import com.example.theaterservice.entity.Theater;
import com.example.theaterservice.exception.ShowConflictException;
import com.example.theaterservice.exception.TheaterNotFoundException;
import com.example.theaterservice.feign.MovieFeignClient;
import com.example.theaterservice.mapper.TheaterMapper;
import com.example.theaterservice.repository.TheaterRepository;
import com.example.theaterservice.service.ITheaterService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TheaterServiceImpl implements ITheaterService {

    private final TheaterRepository theaterRepository;
    private final MovieFeignClient movieFeignClient;


    @Override
    public TheaterResponse createTheater(TheaterRequest theaterRequest) {
        Theater theater = TheaterMapper.mapToTheater(theaterRequest);
        Theater savedTheater = theaterRepository.save(theater);
        return TheaterMapper.mapToTheaterResponse(savedTheater);
    }

    @Override
    public TheaterResponse getTheaterById(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new TheaterNotFoundException("Theater not found with id: " + id));
        return TheaterMapper.mapToTheaterResponse(theater);
    }

    @Override
    public List<TheaterResponse> getAllTheaters() {
        return theaterRepository.findAll().stream()
                .map(TheaterMapper::mapToTheaterResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean theaterExists(Long theaterId) {
        return theaterRepository.existsById(theaterId);
    }


    @Override
    @Transactional
    public TheaterWithMovieResponse assignMovieToTheater(Long theaterId, Long movieId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new TheaterNotFoundException("Theater not found with id: " + theaterId));

        // Verify movie exists using Feign client
        MovieResponse movie = movieFeignClient.getMovieById(movieId);

        // Check if theater already has a movie
        if (theater.getCurrentMovieId() != null) {
            throw new ShowConflictException("Theater already has an assigned movie");
        }

        theater.setCurrentMovieId(movieId);
        Theater updatedTheater = theaterRepository.save(theater);

        return TheaterMapper.mapToTheaterWithMovieResponse(updatedTheater, movie);


    }

    @Override
    public List<TheaterWithMovieResponse> getTheatersByMovie(Long movieId) {
        return theaterRepository.findByCurrentMovieId(movieId).stream()
                .map(theater -> {
                    MovieResponse movie = movieFeignClient.getMovieById(movieId);
                    return TheaterMapper.mapToTheaterWithMovieResponse(theater, movie);
                })
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public List<TheaterAssignmentResponse> assignMovieToMultipleTheaters(BatchAssignmentRequest request) {
        // Verify movie exists
        MovieResponse movie = movieFeignClient.getMovieById(request.getMovieId());

        return request.getTheaterIds().stream()
                .map(theaterId -> {
                    try {
                        Theater theater = theaterRepository.findById(theaterId)
                                .orElseThrow(() -> new TheaterNotFoundException("Theater not found with id: " + theaterId));

                        if (theater.getCurrentMovieId() != null) {
                            return TheaterMapper.mapToAssignmentResponse(
                                    theater,
                                    false,
                                    "Theater already has an assigned movie");
                        }

                        theater.setCurrentMovieId(request.getMovieId());
                        theaterRepository.save(theater);

                        return TheaterMapper.mapToAssignmentResponse(
                                theater,
                                true,
                                "Movie assigned successfully");
                    } catch (Exception e) {
                        Theater theater = new Theater();
                        theater.setTheaterId(theaterId);
                        return TheaterMapper.mapToAssignmentResponse(
                                theater,
                                false,
                                e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public TheaterWithMovieResponse updateMovieInTheater(Long theaterId, Long newMovieId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new TheaterNotFoundException("Theater not found with id: " + theaterId));

        // Verify new movie exists
        MovieResponse movie = movieFeignClient.getMovieById(newMovieId);

        theater.setCurrentMovieId(newMovieId);
        Theater updatedTheater = theaterRepository.save(theater);

        return TheaterMapper.mapToTheaterWithMovieResponse(updatedTheater, movie);
    }


}

