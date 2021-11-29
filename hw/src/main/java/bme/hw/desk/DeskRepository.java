package bme.hw.desk;

import bme.hw.entities.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeskRepository extends JpaRepository<Desk, Long> {
    List<Desk> findAllBySeatsIs(Long seats);
}
