package com.facility_manager.facility_reservation_manager.service;

import com.facility_manager.facility_reservation_manager.exception.CapacityFullException;
import com.facility_manager.facility_reservation_manager.model.Reservation;
//import com.facility_manager.facility_reservation_manager.model.ReservationDTO;
import com.facility_manager.facility_reservation_manager.repos.CapacityRepository;
import com.facility_manager.facility_reservation_manager.repos.ReservationRepository;
import com.facility_manager.facility_reservation_manager.repos.UserRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    private final CapacityRepository capacityRepository;

    public ReservationService(final ReservationRepository reservationRepository,
            final UserRepository userRepository, final CapacityRepository capacityRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.capacityRepository = capacityRepository;

    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation get(final Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//    public Long create(final Reservation reservation) {
//        return reservationRepository.save(reservation).getId();
//    }

    public Long create(final Reservation reservation) {
        int capacity = capacityRepository.findByFacilityType(reservation.getFacilityType()).getCapacity();
        int overlappingReservations = reservationRepository
                .findByReservationDateAndFacilityTypeAndStartTimeLessThanAndEndTimeGreaterThan(
                        reservation.getReservationDate(),
                         reservation.getFacilityType(),
                         reservation.getEndTime(),reservation.getStartTime()).size();
       // .findOverlappingReservations(reservation.getReservationDate(),reservation.getStartTime(), reservation.getEndTime()).size();

        System.out.println(overlappingReservations);

        if (overlappingReservations >= capacity) {
            throw new CapacityFullException("This amenity's capacity is full at desired time");
        }

        return reservationRepository.save(reservation).getId();
    }

    public void update(final Long id, final Reservation reservation) {
        final Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        reservationRepository.save(reservation);
    }

    public void delete(final Long id) {
        reservationRepository.deleteById(id);
    }



}
