package bme.hw.auth_user;

import bme.hw.base.AbstractEntity;
import bme.hw.base.auth.Role;
import bme.hw.database.entities.Address;
import bme.hw.database.entities.Order;
import bme.hw.database.entities.Reservation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class AuthUser extends AbstractEntity {

    private String username;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;
}
