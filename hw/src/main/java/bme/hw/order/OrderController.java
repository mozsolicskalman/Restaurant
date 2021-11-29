package bme.hw.order;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.coupon.CouponRepository;
import bme.hw.entities.Coupon;
import bme.hw.entities.Meal;
import bme.hw.entities.Order;
import bme.hw.meal.MealRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;
    private final AuthUserRepository authUserRepository;
    private final MealRepository mealRepository;

    public OrderController(OrderRepository orderRepository, AuthUserRepository authUserRepository, CouponRepository couponRepository,
                    MealRepository mealRepository) {
        this.orderRepository = orderRepository;
        this.authUserRepository = authUserRepository;
        this.couponRepository = couponRepository;
        this.mealRepository = mealRepository;
    }

    @PostMapping("{mealId}")
    public void createOrder(@PathVariable("mealId") Long mealId, @RequestBody OrderDTO orderDTO) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                        () -> new RuntimeException("Logged user not present in database"));
        Meal meal = mealRepository.findById(mealId).orElseThrow(() -> new RuntimeException("Meal is not present in database"));

        Long sum = loggedInUser.getOrders().stream().map(order -> order.getMeal().getPrice()).reduce(0L, Long::sum);
        if(sum / 10000 != (sum + meal.getPrice()) / 10000){
            Coupon coupon = new Coupon();
            coupon.setAuthUser(loggedInUser);
            coupon.setPercentage(20L);
            couponRepository.save(coupon);
        }
        if((loggedInUser.getOrders().size() + 1) % 10 == 0){
            Coupon coupon = new Coupon();
            coupon.setAuthUser(loggedInUser);
            coupon.setPercentage(30L);
            couponRepository.save(coupon);
        }

        Order order = new Order();
        order.setOrderTime(LocalDateTime.now());
        order.setCustomer(loggedInUser);
        order.setOrderType(orderDTO.getOrderType());
        if (orderDTO.getCouponId() != null) {
            Coupon coupon = couponRepository.findById(orderDTO.getCouponId()).orElseThrow(() -> new RuntimeException("Coupon not present in database"));
            coupon.setOrder(order);
        }
        order.setMeal(meal);
        orderRepository.save(order);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                        () -> new RuntimeException("Logged user not present in database"));
        List<Order> orders = orderRepository.findByCustomer_id(loggedInUser.getId());
        if (orders.isEmpty())
            return null;
        return ResponseEntity.ok().body(orders.stream().map(ResponseOrderDTO::new).collect(Collectors.toList()));
    }
}
