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

    private Long feedback;

    private LocalDateTime orderTime;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private AuthUser customer;
}
