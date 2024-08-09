package com.facility_manager.facility_reservation_manager.service;

import com.facility_manager.facility_reservation_manager.model.Reservation;
import com.facility_manager.facility_reservation_manager.model.User;
//import com.facility_manager.facility_reservation_manager.model.UserDTO;
import com.facility_manager.facility_reservation_manager.repos.ReservationRepository;
import com.facility_manager.facility_reservation_manager.repos.UserRepository;
import com.facility_manager.facility_reservation_manager.util.NotFoundException;
import com.facility_manager.facility_reservation_manager.util.ReferencedWarning;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public UserService(final UserRepository userRepository,
            final ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }



    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User get(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final User user) {
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final User user) {
        final User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Reservation userReservation = reservationRepository.findFirstByUser(user);
        if (userReservation != null) {
            referencedWarning.setKey("user.reservation.user.referenced");
            referencedWarning.addParam(userReservation.getId());
            return referencedWarning;
        }
        return null;
    }

}
