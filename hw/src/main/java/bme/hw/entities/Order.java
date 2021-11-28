package bme.hw.entities;

import bme.hw.auth_user.AuthUser;
import bme.hw.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Order extends AbstractEntity {

    private LocalDateTime orderTime;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private AuthUser customer;

    private OrderType orderType;

    private Long price;

    @OneToOne(mappedBy = "order")
    private Coupon coupon;
}
