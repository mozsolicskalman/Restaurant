package bme.hw.order;

import bme.hw.entities.OrderType;
import lombok.Getter;
@Getter
public class OrderDTO {

    private Long price;

    private Long customer_id;

    private OrderType orderType;

    public OrderDTO(Long price, Long customer_id, OrderType orderType, Long coupon_id) {
        this.price = price;
        this.customer_id = customer_id;
        this.orderType = orderType;
        this.coupon_id = coupon_id;
    }

    private Long coupon_id;
}
