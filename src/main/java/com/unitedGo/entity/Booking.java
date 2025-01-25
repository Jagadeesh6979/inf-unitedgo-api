package com.unitedGo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String pnr;
    private Integer noOfPassengers;
    private double totalFare;
    private String status;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Flight bookedFlight;

    @PostPersist
    public void generateIdentityId() {
        this.pnr = "URS" + String.format("%03d", this.id);
    }
}

