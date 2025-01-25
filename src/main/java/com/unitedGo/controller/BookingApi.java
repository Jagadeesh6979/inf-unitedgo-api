package com.unitedGo.controller;

import com.unitedGo.entity.Booking;
import com.unitedGo.model.BookingDetails;
import com.unitedGo.model.GetPnrDetails;
import com.unitedGo.service.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/booking")
@Validated
public class BookingApi {
    private final BookingService bookingService;

    public BookingApi(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/flights/book")
    public ResponseEntity<String> createBooking(@Valid @RequestBody BookingDetails bookingDetails) {
       Booking booking = bookingService.createBooking(bookingDetails.getFlightId(), bookingDetails.getNoOfPassengers());
       String bookingResponse = "Booking Confirmed with PNR: " + booking.getPnr();
       return new ResponseEntity<>(bookingResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{pnr}")
    public ResponseEntity<GetPnrDetails> getPnrDetails(@PathVariable @Pattern(regexp = "^[A-Z]{3}[0-9]{3}$",
            message = "Please enter valid PNR") String pnr) {
        return new ResponseEntity<>(bookingService.getPnrDetails(pnr), HttpStatus.OK);
    }

    @PutMapping("/{pnr}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable @Pattern(regexp = "^[A-Z]{3}[0-9]{3}$",
            message = "Please enter valid PNR") String pnr) {
        return new ResponseEntity<>(bookingService.cancelBooking(pnr), HttpStatus.OK);
    }
}
