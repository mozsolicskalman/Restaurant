package bme.hw.database.entities;

import bme.hw.auth_user.AuthUser;
import bme.hw.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Reservation extends AbstractEntity {

    private LocalDateTime reservationTime;

    @ManyToOne
    @JoinColumn(name = "desk_id", nullable = false)
    private Desk desk;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private AuthUser customer;

}
