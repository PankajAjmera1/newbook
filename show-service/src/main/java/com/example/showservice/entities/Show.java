package com.example.showservice.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showId;

    private Long theaterId;
    private Long movieId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "show")
    private List<Seat> seats;

    // Calculated field
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime) && now.isBefore(endTime);
    }
}