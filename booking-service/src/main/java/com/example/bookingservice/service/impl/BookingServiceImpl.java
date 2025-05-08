////package com.example.bookingservice.service.impl;
////
////import com.example.bookingservice.dto.BookingRequest;
////import com.example.bookingservice.dto.BookingResponse;
////import com.example.bookingservice.dto.Seat;
////import com.example.bookingservice.dto.ShowDetails;
////import com.example.bookingservice.entity.BookedSeat;
////import com.example.bookingservice.entity.Booking;
////import com.example.bookingservice.entity.BookingStatus;
////import com.example.bookingservice.exception.BookingNotFoundException;
////import com.example.bookingservice.feign.PaymentServiceClient;
////import com.example.bookingservice.feign.ShowServiceClient;
////import com.example.bookingservice.mapper.BookingMapper;
////import com.example.bookingservice.repository.BookedSeatRepository;
////import com.example.bookingservice.repository.BookingRepository;
////
////import java.util.List;
////import java.util.stream.Collectors;
////
////@Service
////@RequiredArgsConstructor
////@Transactional
////public class BookingServiceImpl implements IBookingService {
////
////    private final BookingRepository bookingRepository;
////    private final BookedSeatRepository bookedSeatRepository;
////    private final ShowServiceClient showServiceClient;
////    private final PaymentServiceClient paymentServiceClient;
////    private final BookingMapper bookingMapper;
////
////    @Override
////    public BookingResponse createBooking(BookingRequest request) {
////        // 1. Get show details
////        ShowDetails showDetails = showServiceClient.getShowDetails(request.getShowId());
////
////        // 2. Validate and lock seats
////        List<Seat> seats = showServiceClient.lockSeats(
////                request.getShowId(),
////                request.getSeatNumbers()
////        );
////
////        // 3. Calculate total amount
////        Double totalAmount = seats.stream()
////                .mapToDouble(Seat::getPrice)
////                .sum();
////
////        // 4. Create booking entity
////        Booking booking = Booking.builder()
////                .show(Show.builder().id(request.getShowId()).build())
////                .userId(request.getUserId())
////                .totalAmount(totalAmount)
////                .status(BookingStatus.PENDING)
////                .bookingTime(LocalDateTime.now())
////                .build();
////
////        // 5. Save booking
////        Booking savedBooking = bookingRepository.save(booking);
////
////        // 6. Create booked seats
////        List<BookedSeat> bookedSeats = bookingMapper.toBookedSeats(seats, savedBooking);
////        bookedSeatRepository.saveAll(bookedSeats);
////        savedBooking.setBookedSeats(bookedSeats);
////
////        // 7. Initiate payment
////        paymentServiceClient.initiatePayment(
////                new PaymentRequest(
////                        savedBooking.getId(),
////                        totalAmount,
////                        request.getUserId()
////                )
////        );
////
////        // 8. Return response
////        return bookingMapper.toBookingResponse(savedBooking, showDetails);
////    }
////
////    @Override
////    public void confirmBooking(Long bookingId, String paymentId) {
////        Booking booking = bookingRepository.findById(bookingId)
////                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
////
////        // 1. Confirm seats in show service
////        showServiceClient.confirmSeats(
////                booking.getShow().getId(),
////                booking.getBookedSeats().stream()
////                        .map(BookedSeat::getSeatNumber)
////                        .collect(Collectors.toList())
////        );
////
////        // 2. Update booking status
////        booking.setStatus(BookingStatus.CONFIRMED);
////        booking.setPaymentId(paymentId);
////        bookingRepository.save(booking);
////    }
////
////    @Override
////    public List<BookingResponse> getUserBookings(String userId) {
////        return bookingRepository.findByUserId(userId).stream()
////                .map(booking -> {
////                    ShowDetails showDetails = showServiceClient.getShowDetails(booking.getShow().getId());
////                    return bookingMapper.toBookingResponse(booking, showDetails);
////                })
////                .collect(Collectors.toList());
////    }
////}
//
//
//package com.example.bookingservice.service.impl;
//
//import com.example.bookingservice.dto.*;
//import com.example.bookingservice.entity.*;
//import com.example.bookingservice.exception.*;
//import com.example.bookingservice.feign.*;
//import com.example.bookingservice.mapper.BookingMapper;
//import com.example.bookingservice.repository.*;
//import com.example.bookingservice.service.IBookingService;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//@Transactional
//public class BookingServiceImpl implements IBookingService {
//
//    private final BookingRepository bookingRepository;
//    private final BookedSeatRepository bookedSeatRepository;
//    private final ShowServiceClient showServiceClient;
//    private final PaymentServiceClient paymentServiceClient;
//    private final BookingMapper bookingMapper;
//
//    @Override
//    public BookingResponse createBooking(BookingRequest request) {
//        // 1. Validate show exists and get details
//        ShowDetails showDetails = showServiceClient.getShowDetails(request.getShowId());
//
//        // 2. Lock seats (throws exception if unavailable)
//        List<Seat> lockedSeats = showServiceClient.lockSeats(
//                request.getShowId(),
//                request.getSeatNumbers()
//        );
//
//        // 3. Create and save booking
//        Booking booking = buildBookingEntity(request, showDetails, lockedSeats);
//        Booking savedBooking = bookingRepository.save(booking);
//
//        // 4. Create booked seat records
//        createBookedSeats(lockedSeats, savedBooking);
//
//        // 5. Initiate payment (async)
//        initiatePaymentProcess(savedBooking);
//
//        return bookingMapper.toBookingResponse(savedBooking, showDetails);
//    }
//
//    @Override
//    public BookingResponse getBooking(Long bookingId, String userId) {
//        Booking booking = bookingRepository.findByIdAndUserId(bookingId, userId)
//                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
//
//        ShowDetails showDetails = showServiceClient.getShowDetails(booking.getShow().getId());
//        return bookingMapper.toBookingResponse(booking, showDetails);
//    }
//
//    @Override
//    public List<BookingResponse> getUserBookings(String userId) {
//        return bookingRepository.findByUserId(userId).stream()
//                .map(booking -> {
//                    ShowDetails showDetails = showServiceClient.getShowDetails(booking.getShow().getId());
//                    return bookingMapper.toBookingResponse(booking, showDetails);
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void cancelBooking(Long bookingId, String userId) {
//        Booking booking = bookingRepository.findByIdAndUserId(bookingId, userId)
//                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
//
//        if (booking.getStatus() != BookingStatus.PENDING) {
//            throw new BookingOperationException("Only pending bookings can be cancelled");
//        }
//
//        // Release seats
//        showServiceClient.releaseSeats(
//                booking.getShow().getId(),
//                booking.getBookedSeats().stream()
//                        .map(BookedSeat::getSeatNumber)
//                        .collect(Collectors.toList())
//        );
//
//        // Update booking status
//        booking.setStatus(BookingStatus.CANCELLED);
//        bookingRepository.save(booking);
//
//        // Cancel payment (async)
//        paymentServiceClient.cancelPayment(booking);
//    }
//
//    // Helper methods
//    private Booking buildBookingEntity(BookingRequest request, ShowDetails showDetails, List<Seat> seats) {
//        double totalAmount = seats.stream()
//                .mapToDouble(Seat::getPrice)
//                .sum();
//
//        return Booking.builder()
//                .show(Show.builder().id(request.getShowId()).build())
//                .userId(request.getUserId())
//                .totalAmount(totalAmount)
//                .status(BookingStatus.PENDING)
//                .bookingTime(LocalDateTime.now())
//                .build();
//    }
//
//    private void createBookedSeats(List<Seat> seats, Booking booking) {
//        List<BookedSeat> bookedSeats = seats.stream()
//                .map(seat -> BookedSeat.builder()
//                        .seatNumber(seat.getSeatNumber())
//                        .seatType(seat.getSeatType())
//                        .price(seat.getPrice())
//                        .booking(booking)
//                        .build())
//                .collect(Collectors.toList());
//
//        bookedSeatRepository.saveAll(bookedSeats);
//    }
//
//    private void initiatePaymentProcess(Booking booking) {
//        try {
//            paymentServiceClient.initiatePayment(
//                    new PaymentRequest(
//                            booking.getId(),
//                            booking.getTotalAmount(),
//                            booking.getUserId()
//                    )
//            );
//        } catch (Exception e) {
//            // If payment initiation fails, release seats
//            showServiceClient.releaseSeats(
//                    booking.getShow().getId(),
//                    booking.getBookedSeats().stream()
//                            .map(BookedSeat::getSeatNumber)
//                            .collect(Collectors.toList())
//            );
//            throw new PaymentProcessingException("Failed to initiate payment");
//        }
//    }
//}


package com.example.bookingservice.service.impl;

import com.example.bookingservice.dto.*;
import com.example.bookingservice.entity.*;
import com.example.bookingservice.exception.*;
import com.example.bookingservice.feign.*;
import com.example.bookingservice.mapper.BookingMapper;
import com.example.bookingservice.repository.*;
import com.example.bookingservice.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;
    private final BookedSeatRepository bookedSeatRepository;
    private final ShowServiceClient showServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        // 1. Validate show exists and get details
        ShowDetails showDetails = showServiceClient.getShowDetails(request.getShowId());

        // 2. Lock seats (throws exception if unavailable)
        List<Seat> lockedSeats = showServiceClient.lockSeats(
                request.getShowId(),
                request.getSeatNumbers()
        );

        // 3. Create and save booking
        Booking booking = buildBookingEntity(request, showDetails, lockedSeats);
        Booking savedBooking = bookingRepository.save(booking);

        // 4. Create booked seat records
        createBookedSeats(lockedSeats, savedBooking);

        // 5. Initiate payment (async)
        initiatePaymentProcess(savedBooking);

        return bookingMapper.toBookingResponse(savedBooking, showDetails);
    }

    @Override
    public BookingResponse getBooking(Long bookingId, String userId) {
        Booking booking = bookingRepository.findByIdAndUserId(bookingId, userId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        ShowDetails showDetails = showServiceClient.getShowDetails(booking.getShow().getId());
        return bookingMapper.toBookingResponse(booking, showDetails);
    }

    @Override
    public List<BookingResponse> getUserBookings(String userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(booking -> {
                    ShowDetails showDetails = showServiceClient.getShowDetails(booking.getShow().getId());
                    return bookingMapper.toBookingResponse(booking, showDetails);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(Long bookingId, String userId) {
        Booking booking = bookingRepository.findByIdAndUserId(bookingId, userId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BookingOperationException("Only pending bookings can be cancelled");
        }

        // Release seats
        showServiceClient.releaseSeats(
                booking.getShow().getId(),
                booking.getBookedSeats().stream()
                        .map(BookedSeat::getSeatNumber)
                        .collect(Collectors.toList())
        );

        // Update booking status
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    // Helper methods
//    private Booking buildBookingEntity(BookingRequest request, ShowDetails showDetails, List<Seat> seats) {
//        double totalAmount = seats.stream()
//                .mapToDouble(Seat::getPrice)
//                .sum();
//
//        return Booking.builder()
//                .show(Show.builder().id(request.getShowId()).build())
//                .userId(request.getUserId())
//                .totalAmount(totalAmount)
//                .status(BookingStatus.PENDING)
//                .bookingTime(LocalDateTime.now())
//                .build();
//    }
    private Booking buildBookingEntity(BookingRequest request, ShowDetails showDetails, List<Seat> seats) {
        double totalAmount = seats.stream()
                .mapToDouble(Seat::getPrice)
                .sum();

        // Create Show entity from ShowDetails
        Show show = Show.builder()
                .id(showDetails.getId())
                .theaterId(showDetails.getTheaterId())
                .movieId(showDetails.getMovieId())
                .startTime(showDetails.getStartTime())
                .build();

        return Booking.builder()
                .show(show)
                .userId(request.getUserId())
                .totalAmount(totalAmount)
                .status(BookingStatus.PENDING)
                .bookingTime(LocalDateTime.now())
                .build();
    }

    private void createBookedSeats(List<Seat> seats, Booking booking) {
        List<BookedSeat> bookedSeats = bookingMapper.toBookedSeats(seats, booking);
        bookedSeatRepository.saveAll(bookedSeats);
        booking.setBookedSeats(bookedSeats);
    }

    private void initiatePaymentProcess(Booking booking) {
        try {
            paymentServiceClient.initiatePayment(
                    new PaymentRequest(
                            booking.getId(),
                            booking.getTotalAmount(),
                            booking.getUserId()
                    )
            );
        } catch (Exception e) {
            // If payment initiation fails, release seats
            showServiceClient.releaseSeats(
                    booking.getShow().getId(),
                    booking.getBookedSeats().stream()
                            .map(BookedSeat::getSeatNumber)
                            .collect(Collectors.toList())
            );
            throw new PaymentProcessingException("Failed to initiate payment: " + e.getMessage());
        }
    }
}