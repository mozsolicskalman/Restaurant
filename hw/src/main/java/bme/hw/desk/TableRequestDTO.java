package bme.hw.desk;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TableRequestDTO {
    private Long year;
    private Long month;
    private Long day;
    private Long hour;
}
