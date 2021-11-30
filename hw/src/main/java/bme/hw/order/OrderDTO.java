package bme.hw.order;

import bme.hw.entities.OrderType;
import lombok.Getter;

@Getter
public class OrderDTO {
    private OrderType orderType;
    private Long couponId;
    private Long addressId;
}
