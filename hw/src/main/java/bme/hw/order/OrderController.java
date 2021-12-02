package bme.hw.order;

import bme.hw.address.AddressRepository;
import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.base.auth.Role;
import bme.hw.base.auth.RoleSecured;
import bme.hw.coupon.CouponRepository;
import bme.hw.entities.Address;
import bme.hw.entities.Coupon;
import bme.hw.entities.Meal;
import bme.hw.entities.Order;
import bme.hw.entities.OrderType;
import bme.hw.meal.MealRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;
    private final AuthUserRepository authUserRepository;
    private final MealRepository mealRepository;
    private final AddressRepository addressRepository;

    public OrderController(OrderRepository orderRepository, AuthUserRepository authUserRepository, CouponRepository couponRepository,
                    MealRepository mealRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.authUserRepository = authUserRepository;
        this.couponRepository = couponRepository;
        this.mealRepository = mealRepository;
        this.addressRepository = addressRepository;
    }

    @PostMapping("{mealId}")
    @RoleSecured({ Role.ROLE_CUSTOMER })
    public void createOrder(@PathVariable("mealId") Long mealId, @RequestBody OrderDTO orderDTO) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                        () -> new RuntimeException("Logged user not present in database"));
        Meal meal = mealRepository.findById(mealId).orElseThrow(() -> new RuntimeException("Meal is not present in database"));
        Address address = addressRepository.findById(orderDTO.getAddressId()).orElseThrow(() -> new RuntimeException("Address is not present in database"));

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
        order.setAddress(address);
        orderRepository.save(order);
    }

    @GetMapping
    @RoleSecured({ Role.ROLE_CUSTOMER })
    public ResponseEntity<Object> findAll() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                        () -> new RuntimeException("Logged user not present in database"));
        List<Order> orders = orderRepository.findAllByCustomer(loggedInUser);
        return ResponseEntity.ok().body(orders.stream().map(ResponseOrderDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/admin")
    @RoleSecured({ Role.ROLE_ADMIN })
    public ResponseEntity<Object> findAllForAdmin() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok().body(orders.stream().map(ResponseOrderDTO::new).collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    @RoleSecured({ Role.ROLE_ADMIN })
    public void deleteOrder(@PathVariable("id") Long id){
        if(id!=null)
            orderRepository.deleteById(id);
    }

    @PostMapping("/{orderId}/changeDeliveryMethod")
    @RoleSecured({ Role.ROLE_ADMIN })
    public void changeDeliveryMethod(@PathVariable("orderId") Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not present in database"));
        order.setOrderType(order.getOrderType() == OrderType.DELIVERY ? OrderType.IN_PLACE : OrderType.DELIVERY);
        orderRepository.save(order);
    }
}
