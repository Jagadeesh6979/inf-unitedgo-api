package com.unitedGo.mapper;

import com.unitedGo.entity.Booking;
import com.unitedGo.model.GetPnrDetails;
import org.springframework.stereotype.Component;

@Component
public class GetPnrDetailsMapper {
    public GetPnrDetails getPnrDetailsMapper(Booking booking) {
        GetPnrDetails details = new GetPnrDetails();
        details.setFlightId(booking.getBookedFlight().getFlightId());
        details.setSource(booking.getBookedFlight().getSource());
        details.setDestination(booking.getBookedFlight().getDestination());
        details.setDate(booking.getBookedFlight().getAvailableDate());
        details.setDepartureTime(booking.getBookedFlight().getDepartureTime());
        details.setNoOfPassengers(booking.getNoOfPassengers());
        details.setTotalFare(booking.getTotalFare());
        return details;
    }
}
