package com.example.bookingservice.repository;

import com.example.bookingservice.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
    // Custom queries if needed
}