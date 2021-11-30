package bme.hw.reservation;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.entities.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final AuthUserRepository authUserRepository;

    public ReservationController(ReservationRepository reservationRepository, AuthUserRepository authUserRepository) {
        this.reservationRepository = reservationRepository;
        this.authUserRepository = authUserRepository;
    }


    @PutMapping("/updateReservation")
    public void updateReservation(@RequestBody ReservationDTO reservationDTO){
        Optional<Reservation> reservation = reservationRepository.findById(reservationDTO.getId());
        reservation.get().
    }

    @PostMapping("/{reservationId}/feedback/{feedback}")
    public void addFeedback(@PathVariable("reservationId") Long reservationId, @PathVariable("feedback") Long feedback){
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        reservation.get().setFeedback(feedback);
        reservationRepository.save(reservation.get());
    }

    @GetMapping
    public ResponseEntity<Object> getReservation(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new RuntimeException("Logged user not present in database"));
        List<Reservation> reservations = reservationRepository.findAllByCustomer(loggedInUser);
        return ResponseEntity.ok().body(reservations.stream().map(reservation -> new ReservationResponseDTO(reservation.getReservationTime(),
                        reservation.getDesk().getId(),
                        reservation.getDesk().getSeats(),
                        loggedInUser.getId(),
                        loggedInUser.getUsername())).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAddress(@PathVariable("id") Long id){
        if(id!=null)
            reservationRepository.deleteById(id);
    }
}
