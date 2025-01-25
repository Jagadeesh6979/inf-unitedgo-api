package com.unitedGo.util;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class Validators {
    public Long validateDepartureTime(LocalDate availableDate, String departureTime) {
        // Parse the departure time string into LocalTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(departureTime, timeFormatter);

        // Combine LocalDate and LocalTime into LocalDateTime
        LocalDateTime dateTime = LocalDateTime.of(availableDate, time);

        // Get the current time
        LocalDateTime now = LocalDateTime.now();

        // Calculate the difference
        Duration duration = Duration.between(now, dateTime);

        System.out.println("Minutes before departure time: " + duration.toMinutes());
        return duration.toMinutes();
    }

}
