package com.unitedGo.service.impl;

import com.unitedGo.entity.Booking;
import com.unitedGo.entity.Flight;
import com.unitedGo.mapper.GetPnrDetailsMapper;
import com.unitedGo.model.GetPnrDetails;
import com.unitedGo.repository.BookingRepository;
import com.unitedGo.repository.FlightRepository;
import com.unitedGo.service.BookingService;
import com.unitedGo.util.IdGenerator;
import com.unitedGo.util.Validators;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final IdGenerator idGenerator;
    private final GetPnrDetailsMapper getPnrDetailsMapper;
    private final Validators validators;

    @Autowired
    private Environment environment;

    public BookingServiceImpl(FlightRepository flightRepository, BookingRepository bookingRepository, IdGenerator idGenerator, GetPnrDetailsMapper getPnrDetailsMapper, Validators validators) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.idGenerator = idGenerator;
        this.getPnrDetailsMapper = getPnrDetailsMapper;
        this.validators = validators;
    }

    @Override
    public Booking createBooking(String flightId, Integer noOfPassengers) {
        Booking booking = new Booking();
       Optional<Flight> flight = flightRepository.findById(flightId);
       Flight availableFlight = flight.
               orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, Objects.requireNonNull(environment.getProperty("booking.flight.notfound"))));
       if (noOfPassengers > availableFlight.getAvailableSeats()) {
           throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, Objects.requireNonNull(environment.getProperty("booking.flight.seats.notavailable")));
       }else {
           booking.setPnr(idGenerator.generateId());
           booking.setNoOfPassengers(noOfPassengers);
           booking.setTotalFare(availableFlight.getFare() * noOfPassengers);
           booking.setBookedFlight(availableFlight);
           booking.setStatus("booked");
           bookingRepository.save(booking);
           availableFlight.setAvailableSeats(availableFlight.getAvailableSeats() - noOfPassengers);
           flightRepository.save(availableFlight);
       }
        return booking;
    }

    @Override
    public GetPnrDetails getPnrDetails(String pnr) {
       Booking pnrDetails = bookingRepository.findByPnr(pnr);
       if (pnrDetails == null) {
           throw new HttpClientErrorException(HttpStatus.NOT_FOUND, pnr);
       }
        return getPnrDetailsMapper.getPnrDetailsMapper(pnrDetails);
    }

    @Override
    public String cancelBooking(String pnr) {
        String cancelMessage = "";
        Booking booking = bookingRepository.findByPnr(pnr);
        if (booking == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "PNR not found");
        }
        String departureTime = booking.getBookedFlight().getDepartureTime();
        LocalDate availableDate = booking.getBookedFlight().getAvailableDate();
        Long currentTimeMinutes = validators.validateDepartureTime(availableDate,departureTime);
        if (currentTimeMinutes < 120) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Cancellation is not allowed. It must be done at least 2 hours before the departure time.");
        }
        if (booking.getStatus().equals("booked")) {
            booking.setStatus("cancelled");
            bookingRepository.save(booking);
            cancelMessage = "Booking Cancelled";
            // To add cancelled seats to available seats
            Flight flight = booking.getBookedFlight();
            flight.setAvailableSeats(flight.getAvailableSeats() + booking.getNoOfPassengers());
            flightRepository.save(flight);
            System.out.println(flight);
        }else cancelMessage = "Booking already cancelled by PNR: " + pnr;

        return cancelMessage;
    }


}
