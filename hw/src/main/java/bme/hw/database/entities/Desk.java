package bme.hw.database.entities;

import bme.hw.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class Desk extends AbstractEntity {
    @OneToMany(mappedBy = "desk")
    private List<Reservation> reservations;
}


