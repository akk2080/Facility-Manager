package com.facility_manager.facility_reservation_manager.service;

import com.facility_manager.facility_reservation_manager.domain.Reservation;
import com.facility_manager.facility_reservation_manager.domain.User;
import com.facility_manager.facility_reservation_manager.model.UserDTO;
import com.facility_manager.facility_reservation_manager.repos.ReservationRepository;
import com.facility_manager.facility_reservation_manager.repos.UserRepository;
import com.facility_manager.facility_reservation_manager.util.NotFoundException;
import com.facility_manager.facility_reservation_manager.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public UserService(final UserRepository userRepository,
            final ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        return user;
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
