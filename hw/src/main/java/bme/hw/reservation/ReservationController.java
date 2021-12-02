package bme.hw.reservation;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.base.auth.Role;
import bme.hw.base.auth.RoleSecured;
import bme.hw.desk.DeskRepository;
import bme.hw.desk.ReserveTableRequestDTO;
import bme.hw.entities.Desk;
import bme.hw.entities.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final AuthUserRepository authUserRepository;
    private final DeskRepository deskRepository;

    public ReservationController(ReservationRepository reservationRepository, AuthUserRepository authUserRepository, DeskRepository deskRepository) {
        this.reservationRepository = reservationRepository;
        this.authUserRepository = authUserRepository;
        this.deskRepository = deskRepository;
    }

    @PostMapping("/{reservationId}/feedback/{feedback}")
    @RoleSecured({ Role.ROLE_CUSTOMER, Role.ROLE_ADMIN })
    public void sendFeedback(@PathVariable("reservationId") Long reservationId, @PathVariable("feedback") Long feedback){
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                        () -> new RuntimeException("Reservation not present in database"));
        reservation.setFeedback(feedback == 0L ? null : feedback);
        reservationRepository.save(reservation);
    }

    @GetMapping
    @RoleSecured({ Role.ROLE_CUSTOMER })
    public ResponseEntity<Object> getReservations(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new RuntimeException("Logged user not present in database"));
        List<Reservation> reservations = reservationRepository.findAllByCustomer(loggedInUser);
        return ResponseEntity.ok().body(reservations.stream().map(ReservationResponseDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/admin")
    @RoleSecured({ Role.ROLE_ADMIN })
    public ResponseEntity<Object> getReservationsForAdmin(){
        return ResponseEntity.ok().body(reservationRepository.findAll().stream().map(ReservationResponseDTO::new).collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    @RoleSecured({ Role.ROLE_ADMIN })
    public void deleteReservation(@PathVariable("id") Long id){
        if(id!=null)
            reservationRepository.deleteById(id);
    }

    @PostMapping("/{reservationId}/editSeatNumber/{seats}")
    @RoleSecured({ Role.ROLE_ADMIN })
    public void editSeatNumber(@PathVariable("reservationId") Long reservationId, @PathVariable("seats") Long seats, @RequestBody ReserveTableRequestDTO requestDTO){
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                        () -> new RuntimeException("Reservation not present in database"));
        Desk desk = deskRepository.findAllBySeatsIs(seats).stream().filter(d -> d.getReservations().stream()
                        .noneMatch(r -> r.getReservationTime().getDayOfYear() == LocalDateTime.of(requestDTO.getYear().intValue(),
                                        requestDTO.getMonth().intValue(), requestDTO.getDay().intValue(), 0, 0).getDayOfYear() && requestDTO.getHour() == r
                                        .getReservationTime().getHour())).findFirst().orElseThrow(() -> new RuntimeException("No empty table!"));
        reservation.setDesk(desk);
        reservationRepository.save(reservation);
    }
}
