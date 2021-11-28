package bme.hw.order;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.coupon.CouponRepository;
import bme.hw.desk.DeskRepository;
import bme.hw.entities.Coupon;
import bme.hw.entities.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {


    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository, AuthUserRepository authUserRepository, CouponRepository couponRepository) {
        this.orderRepository = orderRepository;
        this.authUserRepository = authUserRepository;
        this.couponRepository = couponRepository;
    }

    private final AuthUserRepository authUserRepository;
    private final CouponRepository couponRepository;


    @PostMapping
    public void createOrder(@RequestBody OrderDTO orderDTO){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new RuntimeException("Logged user not present in database"));
        Order order = new Order();
        order.setOrderTime(LocalDateTime.now());
        order.setCustomer(loggedInUser);
        order.setOrderType(orderDTO.getOrderType());
        if (orderDTO.getCoupon_id()!=null){
            Optional<Coupon> coupon = couponRepository.findById(orderDTO.getCoupon_id());
            order.setCoupon(coupon.get());
            order.setPrice(orderDTO.getPrice()*(1-coupon.get().getPercentage()));
        } else {
            order.setPrice(orderDTO.getPrice());
        }
        orderRepository.save(order);
    }

    @GetMapping
    public List<ResponseOrderDTO> getOrders(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new RuntimeException("Logged user not present in database"));
        List<Order> orders =orderRepository.findByCustomer_id(loggedInUser.getId());
        if(orders.isEmpty())
            return null;
        List<ResponseOrderDTO> orderDTOs = new ArrayList<>();
        orders.forEach(o -> orderDTOs.add(new ResponseOrderDTO(
                o.getOrderTime(),
                o.getCustomer().getId(),
                o.getOrderType(),
                o.getPrice(),
                o.getCoupon().getPercentage())));
        return orderDTOs;
    }
}
