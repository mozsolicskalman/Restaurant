package bme.hw.reservation;

import bme.hw.auth_user.AuthUser;
import bme.hw.entities.Desk;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class ReservationResponseDTO {

    private LocalDateTime reservationTime;

    private Long desk_id;

    private Long seats;

    private Long customer_id;

    private String username;


    public ReservationResponseDTO(LocalDateTime reservationTime, Long desk_id, Long seats, Long customer_id, String username) {
        this.reservationTime = reservationTime;
        this.desk_id = desk_id;
        this.seats = seats;
        this.customer_id = customer_id;
        this.username = username;
    }
}
