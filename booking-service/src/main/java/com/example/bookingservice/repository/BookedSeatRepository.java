package com.example.bookingservice.repository;

import com.example.bookingservice.entity.BookedSeat;
import com.example.bookingservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedSeatRepository extends JpaRepository<BookedSeat, Long> {
    List<BookedSeat> findByBooking(Booking booking);
}