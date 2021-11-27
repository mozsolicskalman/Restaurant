package bme.hw.auth_user;

import bme.hw.base.AbstractEntity;
import bme.hw.base.auth.Role;
import bme.hw.database.entities.Reservation;


import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
public class AuthUser extends AbstractEntity {

    private String username;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations;

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
