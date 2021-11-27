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
public class Table extends AbstractEntity {
    @OneToMany(mappedBy = "table")
    private List<Reservation> reservations;
}


