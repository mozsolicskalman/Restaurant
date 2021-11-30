package bme.hw.order;

import bme.hw.auth_user.AuthUser;
import bme.hw.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomer(AuthUser customer);
}
