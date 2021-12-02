package bme.hw.desk;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.base.auth.Role;
import bme.hw.base.auth.RoleSecured;
import bme.hw.entities.Desk;
import bme.hw.entities.Reservation;
import bme.hw.reservation.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/tables")
public class DeskController {
    private final DeskRepository deskRepository;
    private final AuthUserRepository authUserRepository;
    private final ReservationRepository reservationRepository;

    public DeskController(DeskRepository deskRepository, AuthUserRepository authUserRepository, ReservationRepository reservationRepository) {
        this.deskRepository = deskRepository;
        this.authUserRepository = authUserRepository;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/reserve/{seatNumber}")
    @RoleSecured({ Role.ROLE_CUSTOMER })
    public void reserve(@PathVariable("seatNumber") Long seatNumber, @RequestBody ReserveTableRequestDTO requestDTO) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                        () -> new RuntimeException("Logged user not present in database"));
        Desk desk = deskRepository.findAllBySeatsIs(seatNumber).stream().filter(
                        d -> d.getReservations().stream().noneMatch(r -> r.getReservationTime().getDayOfYear() == LocalDateTime.of(
                                        requestDTO.getYear().intValue(), requestDTO.getMonth().intValue(), requestDTO.getDay().intValue(), 0, 0).getDayOfYear()
                                        && requestDTO.getHour() == r.getReservationTime().getHour())).findFirst().orElseThrow(
                        () -> new RuntimeException("No empty table!"));
        Reservation reservation = new Reservation();
        reservation.setDesk(desk);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setCustomer(loggedInUser);
        reservationRepository.save(reservation);
    }

    @PostMapping
    @RoleSecured({ Role.ROLE_CUSTOMER, Role.ROLE_ADMIN })
    public ResponseEntity<Object> findAllForUser(@RequestBody TableRequestDTO requestDTO) {
        List<Desk> bannedDesks = new LinkedList<>();
        reservationRepository.findAll().stream().filter(r -> r.getReservationTime().getDayOfYear() == LocalDateTime.of(requestDTO.getYear().intValue(),
                        requestDTO.getMonth().intValue(), requestDTO.getDay().intValue(), 0, 0).getDayOfYear() && requestDTO.getHour() == r.getReservationTime()
                        .getHour()).forEach(reservation -> bannedDesks.add(reservation.getDesk()));
        return ResponseEntity.ok().body(deskRepository.findAll().stream().filter(desk -> !bannedDesks.contains(desk)).map(TableResponseDTO::new)
                        .collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAddress(@PathVariable("id") Long id) {
        if (id != null)
            deskRepository.deleteById(id);
    }
}
