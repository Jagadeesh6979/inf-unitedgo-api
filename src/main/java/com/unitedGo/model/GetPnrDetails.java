package com.unitedGo.model;


import lombok.Data;

import java.time.LocalDate;

@Data
public class GetPnrDetails {
    private String flightId;
    private Integer noOfPassengers;
    private String source;
    private String destination;
    private String departureTime;
    private LocalDate date;
    private double totalFare;
}
