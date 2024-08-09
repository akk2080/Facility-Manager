package com.facility_manager.facility_reservation_manager.repos;

import com.facility_manager.facility_reservation_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
