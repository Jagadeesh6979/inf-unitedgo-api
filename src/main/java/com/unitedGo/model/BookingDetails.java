package com.unitedGo.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BookingDetails {
    @NotNull(message = "{booking.blank.flightId}")
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$", message = "Please enter valid flight id.")
    private String flightId;
    @NotNull(message = "{booking.blank.passengers}")
    @Min(value = 1, message = "Number of passengers more than 0 and less than 5")
    @Max(value = 4, message = "Number of passengers more than 0 and less than 5")
    private Integer noOfPassengers;

}
