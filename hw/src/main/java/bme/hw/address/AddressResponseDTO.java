package bme.hw.address;

import bme.hw.entities.Address;
import lombok.Getter;

@Getter
public class AddressResponseDTO {
    private final Long id;
    private final String address;

    public AddressResponseDTO(Address address) {
        this.id = address.getId();
        this.address = address.getAddress();
    }
}
