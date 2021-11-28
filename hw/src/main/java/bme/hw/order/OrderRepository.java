package bme.hw.order;

import bme.hw.database.entities.Desk;
import bme.hw.database.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
