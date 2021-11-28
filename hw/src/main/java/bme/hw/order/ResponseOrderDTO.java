package bme.hw.order;

import bme.hw.auth_user.AuthUser;
import bme.hw.entities.Coupon;
import bme.hw.entities.OrderType;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

public class ResponseOrderDTO {

    private LocalDateTime orderTime;

    private Long customer_id;

    private OrderType orderType;

    private Long price;

    private Long couponpercentage;

    public ResponseOrderDTO(LocalDateTime orderTime, Long customer_id, OrderType orderType, Long price, Long couponpercentage) {
        this.orderTime = orderTime;
        this.customer_id = customer_id;
        this.orderType = orderType;
        this.price = price;
        this.couponpercentage = couponpercentage;
    }
}
