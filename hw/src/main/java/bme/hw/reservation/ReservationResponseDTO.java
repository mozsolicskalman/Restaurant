package bme.hw.reservation;

import bme.hw.entities.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationResponseDTO {

    private Long id;
    private LocalDateTime reservationTime;
    private Long seats;
    private Long feedback;

    public ReservationResponseDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.reservationTime = reservation.getReservationTime();
        this.seats = reservation.getDesk().getSeats();
        this.feedback = reservation.getFeedback();
    }
}
