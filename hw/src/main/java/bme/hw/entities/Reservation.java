package bme.hw.entities;

import bme.hw.auth_user.AuthUser;
import bme.hw.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@Entity
public class Reservation extends AbstractEntity {
    public Reservation(LocalDateTime reservationTime, Optional<Desk> desk, AuthUser customer) {
        this.reservationTime = reservationTime;
        this.desk = desk;
        this.customer = customer;
    }

    private LocalDateTime reservationTime;

    private Long feedback;

    @ManyToOne
    @JoinColumn(name = "desk_id", nullable = false)
    private Desk desk;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private AuthUser customer;

}
