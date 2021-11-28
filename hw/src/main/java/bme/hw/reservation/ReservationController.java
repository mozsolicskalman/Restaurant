package bme.hw.reservation;

import bme.hw.entities.Reservation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<Reservation> getReservations (){
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations;
    }

    @PutMapping("/updateReservation")
    public void updateReservation(@RequestBody ReservationDTO reservationDTO){
        Optional<Reservation> reservation = reservationRepository.findById(reservationDTO.getId());
        reservation.get().
    }

}
