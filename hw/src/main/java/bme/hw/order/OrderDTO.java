package bme.hw.order;

import bme.hw.auth_user.AuthUser;
import bme.hw.entities.OrderType;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class OrderDTO {
    public OrderDTO(Long feedback, Long customer_id, OrderType orderType, Long coupon_id) {
        this.feedback = feedback;
        this.customer_id = customer_id;
        this.orderType = orderType;
        this.coupon_id = coupon_id;
    }

    private Long feedback;

    private Long customer_id;

    private OrderType orderType;

    private Long coupon_id;
}
