package com.example.showservice.mapper;

import com.example.showservice.dto.MovieResponse;
import com.example.showservice.dto.SeatResponse;
import com.example.showservice.dto.ShowResponse;
import com.example.showservice.dto.TheaterResponse;
import com.example.showservice.entities.Seat;
import com.example.showservice.entities.Show;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShowMapper {

    public static SeatResponse mapToSeatResponse(Seat seat) {
        return SeatResponse.builder()
                .seatId(seat.getSeatId())
                .seatNumber(seat.getSeatNumber())
                .type(seat.getType().name()) // Convert enum to string
                .price(seat.getPrice())
                .isBooked(seat.isBooked())
                .showId(seat.getShow().getShowId())
                .build();
    }

//    public static ShowResponse mapToShowResponse(
//            Show show,
//            TheaterResponse theater,
//            MovieResponse movie) {
//
//        List<SeatResponse> seatResponses = show.getSeats().stream()
//                .map(ShowMapper::mapToSeatResponse)
//                .collect(Collectors.toList());
//
//        return ShowResponse.builder()
//                .showId(show.getShowId())
//                .theaterId(show.getTheaterId())
//                .movieId(show.getMovieId())
//                .theaterName(theater.getName())
//                .movieName(movie.getMovieName())
//                .startTime(show.getStartTime())
//                .endTime(show.getEndTime())
//                .seats(seatResponses)
//                .availableSeats((int) seatResponses.stream()
//                        .filter(seat -> !seat.isBooked()).count())
//                .build();
//    }

    public static ShowResponse mapToShowResponse(
            Show show,
            TheaterResponse theater,
            MovieResponse movie) {

        List<SeatResponse> seatResponses = show.getSeats().stream()
                .map(ShowMapper::mapToSeatResponse)
                .collect(Collectors.toList());

        return ShowResponse.builder()
                .showId(show.getShowId())
                .theaterId(show.getTheaterId())
                .movieId(show.getMovieId())
                .theaterName(theater != null ? theater.getName() : "Unknown Theater")
                .movieName(movie != null ? movie.getMovieName() : "Unknown Movie")
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .seats(seatResponses)
                .availableSeats((int) seatResponses.stream()
                        .filter(seat -> !seat.isBooked()).count())
                .build();
    }
}