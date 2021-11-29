package bme.hw.entities;

import bme.hw.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class Meal extends AbstractEntity {
    private String name;
    private Long price;
    @OneToMany(mappedBy = "meal")
    private List<Order> orders;
}
