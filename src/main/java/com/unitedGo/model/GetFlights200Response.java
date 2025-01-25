package com.unitedGo.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;
@Data
public class GetFlights200Response {
    private String flightId;
    private String airLine;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private Integer availableSeats;
    private LocalDate availableDate;
    private double fare;
}
