package com.facility_manager.facility_reservation_manager;

import com.facility_manager.facility_reservation_manager.model.Capacity;
import com.facility_manager.facility_reservation_manager.model.FacilityType;
import com.facility_manager.facility_reservation_manager.model.Reservation;
import com.facility_manager.facility_reservation_manager.model.User;
import com.facility_manager.facility_reservation_manager.repos.CapacityRepository;
import com.facility_manager.facility_reservation_manager.repos.ReservationRepository;
import com.facility_manager.facility_reservation_manager.repos.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@EnableJpaAuditing
public class FacilityReservationManagerApplication {
    private Map<FacilityType, Integer> initialCapacities =
            new HashMap<>() {
                {
                    put(FacilityType.GYM, 20);
                    put(FacilityType.POOL, 4);
                    put(FacilityType.SAUNA, 1);
                }
            };
    public static void main(final String[] args) {
        SpringApplication.run(FacilityReservationManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository,
                                      ReservationRepository reservationRepository, CapacityRepository capacityRepository) {
        return (args) -> {
            User user = userRepository.save(new User());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Reservation reservation = Reservation.builder()
                    .reservationDate(localDate)
                    .startTime(LocalTime.of(12, 00))
                    .endTime(LocalTime.of(13, 00))
                    .user(user)
                    .facilityType(FacilityType.POOL)
                    .build();

            reservationRepository.save(reservation);

            for (FacilityType facilityType : initialCapacities.keySet()) {
                capacityRepository.save(new Capacity(facilityType, initialCapacities.get(facilityType)));
            }
        };
    }
}
