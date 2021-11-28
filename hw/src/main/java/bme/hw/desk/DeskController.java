package bme.hw.desk;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.entities.Desk;
import bme.hw.entities.Reservation;
import bme.hw.reservation.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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

    @PostMapping("/reserve/{id}")
    public void reserve(@PathVariable("id") long id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                        () -> new RuntimeException("Logged user not present in database"));
        LocalDateTime now = LocalDateTime.now();
        List<Reservation> reservations = reservationRepository.findAll().stream().filter(
                        r -> r.getDesk().getId() == id && r.getReservationTime().getDayOfYear() == now.getDayOfYear()
                                        && now.getHour() - r.getReservationTime().getHour() < 2).collect(Collectors.toList());
        if (!reservations.isEmpty()) {
            throw new RuntimeException("Table is already reserved");
        }
        Optional<Desk> desk = deskRepository.findById(id);
        if (desk.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setReservationTime(now);
            reservation.setCustomer(loggedInUser);
            reservation.setDesk(desk.get());
            reservationRepository.save(reservation);
        }
    }

    @PostMapping
    public ResponseEntity<Object> findAllForUser(TableRequestDTO requestDTO) {
        List<Desk> bannedDesks = new LinkedList<>();
        reservationRepository.findAll().stream().filter(
                        r -> r.getReservationTime().getDayOfYear() == LocalDateTime.of(requestDTO.getYear().intValue(), requestDTO.getMonth().intValue(), requestDTO.getDay().intValue(), 0, 0).getDayOfYear() && requestDTO.getHour() == r
                                        .getReservationTime().getHour()).forEach(reservation -> bannedDesks.add(reservation.getDesk()));
        return ResponseEntity.ok().body(deskRepository.findAll().stream().filter(desk -> !bannedDesks.contains(desk)).map(TableResponseDTO::new).collect(Collectors.toList()));
    }
}
