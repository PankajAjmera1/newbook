package com.example.showservice.repository;

import com.example.showservice.entities.Seat;
import com.example.showservice.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    // Count seats by show and seat numbers
    long countByShowAndSeatNumberIn(Show show, List<String> seatNumbers);

    // Find already booked seats
    List<Seat> findByShowAndSeatNumberInAndIsBookedTrue(Show show, List<String> seatNumbers);

    // Bulk update to mark seats as booked
    @Modifying
    @Query("UPDATE Seat s SET s.isBooked = true " +
            "WHERE s.show = :show AND s.seatNumber IN :seatNumbers AND s.isBooked = false")
    int markSeatsAsBooked(@Param("show") Show show,
                          @Param("seatNumbers") List<String> seatNumbers);

    // Additional useful methods
    List<Seat> findByShow(Show show);

    List<Seat> findByShowAndIsBookedFalse(Show show);

    @Query("SELECT s FROM Seat s WHERE s.show = :show AND s.seatNumber IN :seatNumbers")
    List<Seat> findByShowAndSeatNumbers(@Param("show") Show show,
                                        @Param("seatNumbers") List<String> seatNumbers);
}