package bme.hw.reservation;

import bme.hw.auth_user.AuthUser;
import bme.hw.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByCustomer(AuthUser customer);
}
