package bme.hw.reservation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDTO {
    private long id;
    private long customer_id;
    private long desk_id;
    private LocalDateTime time;

    public ReservationDTO(long id, long customer_id, long desk_id, LocalDateTime time) {
        this.id = id;
        this.customer_id = customer_id;
        this.desk_id = desk_id;
        this.time = time;
    }

}
