package bme.hw.database.entities;

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

    @Column(name="FEED_BACK", length=5)
    private int feedBack;

    @Column(name="ORDER_TIME")
    private LocalDateTime orderTime;

    @Column(name="CUSTOMER", length = 50, nullable = false)
    private AuthUser customer;
}
