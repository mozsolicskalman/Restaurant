package bme.hw.order;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.desk.DeskRepository;
import bme.hw.entities.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrderController {
    public OrderController(OrderRepository orderRepository, AuthUserRepository authUserRepository) {
        this.orderRepository = orderRepository;
        this.authUserRepository = authUserRepository;
    }

    private final OrderRepository orderRepository;
    private final AuthUserRepository authUserRepository;


    @PostMapping
    public void createOrder(@RequestBody OrderDTO orderDTO){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new RuntimeException("Logged user not present in database"));
        Order order = new Order();
        order.setOrderTime(LocalDateTime.now());
        order.setCustomer(loggedInUser);

    }
}
