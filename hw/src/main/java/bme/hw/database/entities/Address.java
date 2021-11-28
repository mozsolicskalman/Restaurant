package bme.hw.database.entities;

import bme.hw.auth_user.AuthUser;
import bme.hw.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Address extends AbstractEntity {
    private String address;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private AuthUser customer;
}
