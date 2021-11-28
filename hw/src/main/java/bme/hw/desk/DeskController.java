package bme.hw.desk;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.entities.Desk;
import bme.hw.entities.Reservation;
import bme.hw.reservation.ReservationRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/table")
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
        List<Reservation> reservations = reservationRepository.findAll().stream()
                .filter(r -> r.getDesk().getId() == id
                        && r.getReservationTime().getDayOfYear() == now.getDayOfYear()
                        && now.getHour() - r.getReservationTime().getHour() < 2)
                .collect(Collectors.toList());
        if (!reservations.isEmpty()) {
            throw new RuntimeException("Table is already reserved");
        }
        Optional<Desk> desk = deskRepository.findById(id);
        if (!desk.isEmpty()) {
            reservationRepository.save(new Reservation(now, desk, loggedInUser));
        }
    }
}
