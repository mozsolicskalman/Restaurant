package bme.hw.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAddressDTO {

    private String address;

    private long customer_id;

    public ResponseAddressDTO(String address, long customer_id) {
        this.address = address;
        this.customer_id = customer_id;
    }
}
