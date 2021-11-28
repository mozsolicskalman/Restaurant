package bme.hw.reservation;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.entities.Reservation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final AuthUserRepository authUserRepository;

    public ReservationController(ReservationRepository reservationRepository, AuthUserRepository authUserRepository) {
        this.reservationRepository = reservationRepository;
        this.authUserRepository = authUserRepository;
    }

    @GetMapping
    public List<Reservation> getReservations (){
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations;
    }
/*
    @PutMapping("/updateReservation")
    public void updateReservation(@RequestBody ReservationDTO reservationDTO){
        Optional<Reservation> reservation = reservationRepository.findById(reservationDTO.getId());
        reservation.get().
    }
*/
    @PostMapping("/{reservationId}/feedback/{feedback}")
    public void addFeedback(@PathVariable("reservationId") Long reservationId, @PathVariable("feedback") Long feedback){
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        reservation.get().setFeedback(feedback);
        reservationRepository.save(reservation.get());
    }

    @GetMapping
    public ReservationResponseDTO getReservation(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new RuntimeException("Logged user not present in database"));
        Reservation reservation = reservationRepository.findByCustomer_id(loggedInUser.getId());
        if(reservation == null)
            return null;
        return new ReservationResponseDTO(reservation.getReservationTime(),
                reservation.getDesk().getId(),
                reservation.getDesk().getSeats(),
                loggedInUser.getId(),
                loggedInUser.getUsername());
    }


}
