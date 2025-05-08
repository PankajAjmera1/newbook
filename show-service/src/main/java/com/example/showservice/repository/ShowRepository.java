package com.example.showservice.repository;

import com.example.showservice.entities.Show;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByTheaterIdAndStartTimeBetweenOrTheaterIdAndEndTimeBetween(
            Long theaterId1, LocalDateTime start1, LocalDateTime end1,
            Long theaterId2, LocalDateTime start2, LocalDateTime end2);

    List<Show> findByTheaterId(Long theaterId);
    List<Show> findByMovieId(Long movieId);

    @Query("SELECT s FROM Show s WHERE s.theaterId = :theaterId " +
            "AND ((s.startTime BETWEEN :start AND :end) " +
            "OR (s.endTime BETWEEN :start AND :end))")
    List<Show> findConflictingShows(
            @Param("theaterId") Long theaterId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);


    List<Show> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}
