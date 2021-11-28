package bme.hw.entities;

import bme.hw.auth_user.AuthUser;
import bme.hw.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Coupon extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private AuthUser authUser;

    private Long percentage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
}
