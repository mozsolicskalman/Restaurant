package bme.hw.coupon;

import bme.hw.entities.Coupon;
import lombok.Getter;

@Getter
public class ResponseCouponDTO {
    private Long percentage;
    private Long id;

    public ResponseCouponDTO(Coupon coupon) {
        this.percentage = coupon.getPercentage();
        this.id = coupon.getId();
    }
}
