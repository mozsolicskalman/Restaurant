package bme.hw.desk;

import bme.hw.entities.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeskRepository extends JpaRepository<Desk, Long> {
}
