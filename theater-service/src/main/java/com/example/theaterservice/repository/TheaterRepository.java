package com.example.theaterservice.repository;


import com.example.theaterservice.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    List<Theater> findByCurrentMovieIdAndLocation(Long movieId, String location);
    List<Theater> findByCurrentMovieId(Long movieId);
}