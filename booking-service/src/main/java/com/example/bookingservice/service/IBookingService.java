package com.example.bookingservice.service;

import com.example.bookingservice.dto.BookingRequest;
import com.example.bookingservice.dto.BookingResponse;
import java.util.List;

public interface IBookingService {
    BookingResponse createBooking(BookingRequest request);
    BookingResponse getBooking(Long bookingId, String userId);
    List<BookingResponse> getUserBookings(String userId);
    void cancelBooking(Long bookingId, String userId);
}