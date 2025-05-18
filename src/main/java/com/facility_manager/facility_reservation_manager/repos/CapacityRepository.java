package com.facility_manager.facility_reservation_manager.repos;

import com.facility_manager.facility_reservation_manager.model.Capacity;
import com.facility_manager.facility_reservation_manager.model.FacilityType;
import com.facility_manager.facility_reservation_manager.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CapacityRepository extends JpaRepository<Capacity, Long> {


    Capacity findByFacilityType(FacilityType facilityType);
}
