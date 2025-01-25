package com.unitedGo.repository;

import com.unitedGo.entity.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    Booking findByPnr(String pnr);
}
