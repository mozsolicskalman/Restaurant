package bme.hw.desk;

import lombok.Getter;

@Getter
public class ReserveTableRequestDTO {
    private Long year;
    private Long month;
    private Long day;
    private Long hour;
}
