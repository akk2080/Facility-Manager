package com.facility_manager.facility_reservation_manager.repos;

import com.facility_manager.facility_reservation_manager.model.FacilityType;
import com.facility_manager.facility_reservation_manager.model.Reservation;
import com.facility_manager.facility_reservation_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findFirstByUser(User user);

    List<Reservation> findByReservationDateAndFacilityTypeAndStartTimeLessThanAndEndTimeGreaterThan(
            LocalDate reservationDate,
            FacilityType facilityType,
            LocalTime newEnd,
            LocalTime newStart
    );

    @Query("SELECT r FROM Reservation r WHERE r.reservationDate = :date AND " +
            "r.startTime < :newEnd AND r.endTime > :newStart")
    List<Reservation> findOverlappingReservations(
            @Param("date") LocalDate date,
            @Param("newStart") LocalTime newStart,
            @Param("newEnd") LocalTime newEnd
    );

}
