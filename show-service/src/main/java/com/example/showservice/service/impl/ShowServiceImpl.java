//package com.example.showservice.service.impl;
//
//
//import com.example.showservice.dto.MovieResponse;
//import com.example.showservice.dto.ShowRequest;
//import com.example.showservice.dto.ShowResponse;
//import com.example.showservice.dto.TheaterResponse;
//import com.example.showservice.entities.Seat;
//import com.example.showservice.entities.SeatType;
//import com.example.showservice.entities.Show;
//import com.example.showservice.feign.MovieFeignClient;
//import com.example.showservice.feign.TheaterFeignClient;
//import com.example.showservice.repository.SeatRepository;
//import com.example.showservice.repository.ShowRepository;
//import com.example.showservice.service.IShowService;
//import lombok.*;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static com.example.showservice.mapper.ShowMapper.mapToShowResponse;
//
//@Service
//@AllArgsConstructor
//public class ShowServiceImpl implements IShowService {
//    private final ShowRepository showRepository;
//    private final SeatRepository seatRepository;
//    private final TheaterFeignClient theaterClient;
//    private final MovieFeignClient movieClient;
//
//    @Override
//    @Transactional
//    public ShowResponse createShow(ShowRequest request) {
//        // Validate theater and movie
//        TheaterResponse theater = theaterClient.getTheaterById(request.getTheaterId());
//        MovieResponse movie = movieClient.getMovieById(request.getMovieId());
//
//        // Calculate end time
//        LocalDateTime endTime = request.getStartTime()
//                .plusMinutes(request.getDurationMinutes());
//
//        // Check for conflicting shows
//        validateNoConflictingShows(request.getTheaterId(), request.getStartTime(), endTime);
//
//        // Create show
//        Show show = Show.builder()
//                .theaterId(request.getTheaterId())
//                .movieId(request.getMovieId())
//                .startTime(request.getStartTime())
//                .endTime(endTime)
//                .build();
//
//        Show savedShow = showRepository.save(show);
//
//        // Create seats
//        List<Seat> seats = createSeatsForShow(savedShow, request.getSeatsCount());
//        savedShow.setSeats(seats);
//
//        return mapToShowResponse(savedShow, theater, movie);
//    }
//
//
//
//    @Override
//    public List<Seat> createSeatsForShow(Show show, int seatsCount) {
//        List<Seat> seats = new ArrayList<>();
//
//        // Simple seat creation logic - customize as needed
//        for (int i = 1; i <= seatsCount; i++) {
//            SeatType type = i <= 10 ? SeatType.PREMIUM :
//                    i <= 30 ? SeatType.VIP : SeatType.REGULAR;
//
//            double price = type == SeatType.PREMIUM ? 500 :
//                    type == SeatType.VIP ? 350 : 200;
//
//            seats.add(Seat.builder()
//                    .seatNumber("S-" + i)
//                    .type(type)
//                    .price(price)
//                    .isBooked(false)
//                    .show(show)
//                    .build());
//        }
//
//        return seatRepository.saveAll(seats);
//    }
//
//    @Override
//    public void validateNoConflictingShows(Long theaterId, LocalDateTime start, LocalDateTime end) {
//        List<Show> conflictingShows = showRepository.findByTheaterIdAndStartTimeBetweenOrTheaterIdAndEndTimeBetween(
//                theaterId, start, end,
//                theaterId, start, end);
//
//        if (!conflictingShows.isEmpty()) {
//            throw new RuntimeException("Theater already has shows during this time");
//        }
//    }
//
//
//    // Add these methods to your ShowServiceImpl class
//
//    @Override
//    public ShowResponse getShowById(Long id) {
//        Show show = showRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Show not found with id: " + id));
//
//        TheaterResponse theater = theaterClient.getTheaterById(show.getTheaterId());
//        MovieResponse movie = movieClient.getMovieById(show.getMovieId());
//
//        return mapToShowResponse(show, theater, movie);
//    }
//
//    @Override
//    public List<ShowResponse> getShowsByTheater(Long theaterId) {
//        List<Show> shows = showRepository.findByTheaterId(theaterId);
//        return shows.stream()
//                .map(show -> {
//                    TheaterResponse theater = theaterClient.getTheaterById(show.getTheaterId());
//                    MovieResponse movie = movieClient.getMovieById(show.getMovieId());
//                    return mapToShowResponse(show, theater, movie);
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<ShowResponse> getShowsByMovie(Long movieId) {
//        List<Show> shows = showRepository.findByMovieId(movieId);
//        return shows.stream()
//                .map(show -> {
//                    TheaterResponse theater = theaterClient.getTheaterById(show.getTheaterId());
//                    MovieResponse movie = movieClient.getMovieById(show.getMovieId());
//                    return mapToShowResponse(show, theater, movie);
//                })
//                .collect(Collectors.toList());
//    }
//}


package com.example.showservice.service.impl;

import com.example.showservice.dto.*;
import com.example.showservice.entities.*;
import com.example.showservice.exception.*;
import com.example.showservice.feign.*;
import com.example.showservice.repository.*;
import com.example.showservice.service.*;
import feign.FeignException;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;
import static com.example.showservice.mapper.ShowMapper.mapToShowResponse;

@Service
@AllArgsConstructor
public class ShowServiceImpl implements IShowService {
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final TheaterFeignClient theaterClient;
    private final MovieFeignClient movieClient;

    @Override
    @Transactional
    public ShowResponse createShow(ShowRequest request) {
        try {
            // Validate start time is in future with buffer
            validateShowTiming(request.getStartTime(), request.getDurationMinutes());

            // Validate theater and movie exist
            TheaterResponse theater = theaterClient.getTheaterById(request.getTheaterId());
            MovieResponse movie = movieClient.getMovieById(request.getMovieId());

            // Calculate end time
            LocalDateTime endTime = request.getStartTime()
                    .plusMinutes(request.getDurationMinutes());

            // Check for conflicting shows
            validateNoConflictingShows(request.getTheaterId(), request.getStartTime(), endTime);

            // Create show
            Show show = Show.builder()
                    .theaterId(request.getTheaterId())
                    .movieId(request.getMovieId())
                    .startTime(request.getStartTime())
                    .endTime(endTime)
                    .build();

            Show savedShow = showRepository.save(show);

            // Create seats
            List<Seat> seats = createSeatsForShow(savedShow, request.getSeatsCount());
            savedShow.setSeats(seats);

            return mapToShowResponse(savedShow, theater, movie);

        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Theater or Movie not found");
        } catch (FeignException e) {
            throw new ServiceUnavailableException("Dependent service unavailable");
        }
    }

    private void validateShowTiming(LocalDateTime startTime, int durationMinutes) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minimumStartTime = now.plusHours(1); // 1 hour buffer

        if (startTime.isBefore(minimumStartTime)) {
            throw new InvalidShowTimeException(
                    "Show must start at least 1 hour from now. Earliest allowed: " + minimumStartTime
            );
        }

        if (durationMinutes <= 0) {
            throw new InvalidDurationException("Duration must be positive");
        }
    }

    @Override
    public List<Seat> createSeatsForShow(Show show, int seatsCount) {
        if (seatsCount <= 0) {
            throw new InvalidSeatCountException("Seat count must be positive");
        }

        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= seatsCount; i++) {
            SeatType type = determineSeatType(i);
            double price = calculateSeatPrice(type);

            seats.add(Seat.builder()
                    .seatNumber(generateSeatNumber(i))
                    .type(type)
                    .price(price)
                    .isBooked(false)
                    .show(show)
                    .build());
        }
        return seatRepository.saveAll(seats);
    }

    private SeatType determineSeatType(int seatNumber) {
        if (seatNumber <= 10) return SeatType.PREMIUM;
        if (seatNumber <= 30) return SeatType.VIP;
        return SeatType.REGULAR;
    }

    private double calculateSeatPrice(SeatType type) {
        return switch (type) {
            case PREMIUM -> 500;
            case VIP -> 350;
            case REGULAR -> 200;
        };
    }

    private String generateSeatNumber(int seatNumber) {
        return "S-" + seatNumber;
    }

    @Override
    public void validateNoConflictingShows(Long theaterId, LocalDateTime start, LocalDateTime end) {
        List<Show> conflictingShows = showRepository.findConflictingShows(
                theaterId, start.minusMinutes(30), end.plusMinutes(30));

        if (!conflictingShows.isEmpty()) {
            throw new ShowConflictException("Theater has conflicting shows between " +
                    start.minusMinutes(30) + " and " + end.plusMinutes(30));
        }
    }

    @Override
    public ShowResponse getShowById(Long id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + id));

        try {
            TheaterResponse theater = theaterClient.getTheaterById(show.getTheaterId());
            MovieResponse movie = movieClient.getMovieById(show.getMovieId());
            return mapToShowResponse(show, theater, movie);
        } catch (FeignException e) {
            throw new ServiceUnavailableException("Could not fetch theater/movie details");
        }
    }

    @Override
    public List<ShowResponse> getShowsByTheater(Long theaterId) {
        List<Show> shows = showRepository.findByTheaterId(theaterId);
        return transformShowsToResponses(shows);
    }

    @Override
    public List<ShowResponse> getShowsByMovie(Long movieId) {
        List<Show> shows = showRepository.findByMovieId(movieId);
        return transformShowsToResponses(shows);
    }

    private List<ShowResponse> transformShowsToResponses(List<Show> shows) {
        return shows.stream()
                .map(show -> {
                    try {
                        TheaterResponse theater = theaterClient.getTheaterById(show.getTheaterId());
                        MovieResponse movie = movieClient.getMovieById(show.getMovieId());
                        return mapToShowResponse(show, theater, movie);
                    } catch (FeignException e) {
                        // Return partial response if dependent services are unavailable
                        return mapToShowResponse(show, null, null);
                    }
                })
                .collect(Collectors.toList());
    }

//    @Override
//    public List<ShowResponse> getUpcomingShows(int daysAhead) {
//        LocalDateTime start = LocalDateTime.now();
//        LocalDateTime end = start.plusDays(daysAhead);
//        List<Show> shows = showRepository.findByStartTimeBetween(start, end);
//        return transformShowsToResponses(shows);
//    }


    @Override
    public List<ShowResponse> getUpcomingShows(int daysAhead) {
        if (daysAhead <= 0) {
            throw new IllegalArgumentException("Days ahead must be positive");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.plusDays(daysAhead);

        List<Show> shows = showRepository.findByStartTimeBetween(now, endDate);

        return shows.stream()
                .map(show -> {
                    TheaterResponse theater = safelyGetTheater(show.getTheaterId());
                    MovieResponse movie = safelyGetMovie(show.getMovieId());
                    return mapToShowResponse(show, theater, movie);
                })
                .collect(Collectors.toList());
    }
    private TheaterResponse safelyGetTheater(Long theaterId) {
        try {
            return theaterClient.getTheaterById(theaterId);
        } catch (FeignException e) {
            return null; // Return partial response
        }
    }

    private MovieResponse safelyGetMovie(Long movieId) {
        try {
            return movieClient.getMovieById(movieId);
        } catch (FeignException e) {
            return null; // Return partial response
        }
    }


    @Override
    @Transactional
    public void bookSeats(Long showId, List<String> seatNumbers) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + showId));

        // Verify all requested seats exist
        long foundSeatsCount = seatRepository.countByShowAndSeatNumberIn(show, seatNumbers);
        if (foundSeatsCount != seatNumbers.size()) {
            throw new InvalidSeatSelectionException(
                    String.format("Requested %d seats but only found %d",
                            seatNumbers.size(), foundSeatsCount));
        }

        // Check for already booked seats
        List<String> bookedSeats = seatRepository
                .findByShowAndSeatNumberInAndIsBookedTrue(show, seatNumbers)
                .stream()
                .map(Seat::getSeatNumber)
                .collect(Collectors.toList());

        if (!bookedSeats.isEmpty()) {
            throw new SeatAlreadyBookedException(
                    "Seats already booked: " + String.join(", ", bookedSeats));
        }

        // Optimized bulk update
        int updated = seatRepository.markSeatsAsBooked(show, seatNumbers);
        if (updated != seatNumbers.size()) {
            throw new RuntimeException("Failed to book all seats");
        }
    }
}