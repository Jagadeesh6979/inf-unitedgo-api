package com.unitedGo.service;

import com.unitedGo.model.GetFlights200Response;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface FlightService {
    public List<GetFlights200Response> searchFlights(String source, String destination, LocalDate availableDate);
}
