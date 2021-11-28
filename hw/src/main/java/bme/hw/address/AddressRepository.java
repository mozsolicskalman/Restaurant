package bme.hw.address;

import bme.hw.entities.Address;
import bme.hw.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
