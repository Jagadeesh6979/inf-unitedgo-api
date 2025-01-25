package com.unitedGo.service.impl;

import com.unitedGo.entity.Flight;
import com.unitedGo.mapper.FlightMapper;
import com.unitedGo.model.GetFlights200Response;
import com.unitedGo.repository.FlightRepository;
import com.unitedGo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private  final FlightMapper flightMapper;
    @Autowired
    private Environment environment;

    public FlightServiceImpl(FlightRepository flightRepository, FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
    }

    @Override
    public List<GetFlights200Response> searchFlights(String source, String destination, LocalDate availableDate) {
        if (availableDate.isBefore(LocalDate.now())) throw new RuntimeException(environment.getProperty("flight.date.invalid"));
       List<Flight> response =
               flightRepository.findBySourceAndDestinationAndAvailableDate(source, destination, availableDate);
       if (response.isEmpty()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "No flights found on selected date.");
       return flightMapper.getSearchFlightMapper(response);
    }
}
