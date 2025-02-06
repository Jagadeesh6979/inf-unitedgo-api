package com.unitedGo.controller;


import com.unitedGo.model.GetFlights200Response;
import com.unitedGo.service.FlightService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@Validated
@RequestMapping("api")
public class FlightApi {

    private final FlightService flightService;

    public FlightApi(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flights/{source}/{destination}/{date}")
    public ResponseEntity<List<GetFlights200Response>> searchFlights(@PathVariable @NotNull @Pattern(regexp = "[A-Za-z]+",
            message = "Please enter valid source") String source, @PathVariable @NotNull @Pattern(regexp = "[A-Za-z]+",
            message = "Please enter valid destination") String destination, @PathVariable @NotNull LocalDate date) {

        return new ResponseEntity<>(flightService.searchFlights(source, destination, date), HttpStatus.OK) ;
    }
    @GetMapping("/greet")
    public String check(){
        return "Welcome to UnitedGo API";
    }
}
