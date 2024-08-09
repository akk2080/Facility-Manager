package com.facility_manager.facility_reservation_manager.repos;

import com.facility_manager.facility_reservation_manager.model.Reservation;
import com.facility_manager.facility_reservation_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findFirstByUser(User user);

}
