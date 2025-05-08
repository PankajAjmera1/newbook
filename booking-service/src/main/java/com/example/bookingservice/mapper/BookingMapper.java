package com.example.bookingservice.mapper;

import com.example.bookingservice.dto.BookedSeatResponse;
import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.dto.Seat;
import com.example.bookingservice.dto.ShowDetails;
import com.example.bookingservice.entity.BookedSeat;
import com.example.bookingservice.entity.Booking;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

//    public BookingResponse toBookingResponse(Booking booking, ShowDetails showDetails) {
//        return BookingResponse.builder()
//                .id(booking.getId())
//                .showId(booking.getShow().getId())
//                .theaterName(showDetails.getTheaterName())
//                .movieName(showDetails.getMovieName())
//                .showTime(booking.getShow().getStartTime())
//                .seats(toBookedSeatResponses(booking.getBookedSeats()))
//                .totalAmount(booking.getTotalAmount())
//                .status(booking.getStatus())
//                .bookingTime(booking.getBookingTime())
//                .build();
//    }

    public BookingResponse toBookingResponse(Booking booking, ShowDetails showDetails) {
        return BookingResponse.builder()
                .id(booking.getId())
                .showId(booking.getShow().getId())
                .theaterName(showDetails.getTheaterName())
                .movieName(showDetails.getMovieName())
                .showTime(showDetails.getStartTime()) // Use from showDetails
                .seats(toBookedSeatResponses(booking.getBookedSeats()))
                .totalAmount(booking.getTotalAmount())
                .status(booking.getStatus())
                .bookingTime(booking.getBookingTime())
                .build();
    }

    public List<BookedSeatResponse> toBookedSeatResponses(List<BookedSeat> bookedSeats) {
        return bookedSeats.stream()
                .map(this::toBookedSeatResponse)
                .collect(Collectors.toList());
    }

    public BookedSeatResponse toBookedSeatResponse(BookedSeat bookedSeat) {
        return BookedSeatResponse.builder()
                .seatNumber(bookedSeat.getSeatNumber())
                .seatType(bookedSeat.getSeatType())
                .price(bookedSeat.getPrice())
                .build();
    }

    public List<BookedSeat> toBookedSeats(List<Seat> seats, Booking booking) {
        return seats.stream()
                .map(seat -> toBookedSeat(seat, booking))
                .collect(Collectors.toList());
    }

    private BookedSeat toBookedSeat(Seat seat, Booking booking) {
        return BookedSeat.builder()
                .seatNumber(seat.getSeatNumber())
                .seatType(seat.getSeatType())
                .price(seat.getPrice())
                .booking(booking)
                .build();
    }
}

