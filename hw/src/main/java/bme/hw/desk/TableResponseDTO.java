package bme.hw.desk;

import bme.hw.entities.Desk;
import lombok.Getter;

@Getter
public class TableResponseDTO {
    private final Long seats;
    private final Long tableId;

    public TableResponseDTO(Desk desk) {
        this.seats = desk.getSeats();
        this.tableId = desk.getId();
    }
}
