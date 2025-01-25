package com.unitedGo.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class SearchFlight {
    @NotNull
    @Pattern(regexp = "[A-Za-z]+")
    private String source;
    @NotNull
    @Pattern(regexp = "[A-Za-z]+")
    private String destination;
    @NotNull
    @FutureOrPresent
    private LocalDate date;
}
