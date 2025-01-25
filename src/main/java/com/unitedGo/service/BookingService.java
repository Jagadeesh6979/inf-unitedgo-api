package com.unitedGo.service;

import com.unitedGo.entity.Booking;
import com.unitedGo.model.GetPnrDetails;

public interface BookingService {
    public Booking createBooking(String flightId, Integer noOfPassengers);
    public GetPnrDetails getPnrDetails(String pnr);
    public String cancelBooking(String pnr);
}
