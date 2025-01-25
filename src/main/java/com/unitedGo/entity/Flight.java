package com.unitedGo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
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
