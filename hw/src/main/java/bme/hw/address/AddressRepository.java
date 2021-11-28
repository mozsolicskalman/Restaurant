package bme.hw.address;

import bme.hw.database.entities.Desk;
import bme.hw.database.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Reservation, Long> {
}
