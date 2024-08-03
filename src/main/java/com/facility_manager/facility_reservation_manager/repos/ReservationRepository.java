package com.facility_manager.facility_reservation_manager.repos;

import com.facility_manager.facility_reservation_manager.domain.Reservation;
import com.facility_manager.facility_reservation_manager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findFirstByUser(User user);

}
