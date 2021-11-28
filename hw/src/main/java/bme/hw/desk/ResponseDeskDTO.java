package bme.hw.desk;

import bme.hw.entities.Reservation;

import javax.persistence.OneToMany;
import java.util.List;

public class ResponseDeskDTO {

    private Long id;

    public ResponseDeskDTO(Long id, Long seats) {
        this.id = id;
        this.seats = seats;
    }

    private Long seats;
}
