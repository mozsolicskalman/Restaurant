package bme.hw.order;

import bme.hw.entities.Order;
import bme.hw.entities.OrderType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseOrderDTO {

    private LocalDateTime orderTime;

    private OrderType orderType;

    private Long price;

    private Long couponPercentage;

    private String address;

    public ResponseOrderDTO(Order order) {
        this.orderTime = order.getOrderTime();
        this.orderType = order.getOrderType();
        this.price = order.getMeal().getPrice();
        this.couponPercentage = order.getCoupon() == null ? 0 : order.getCoupon().getPercentage();
        this.address = order.getAddress().getAddress();
    }
}
