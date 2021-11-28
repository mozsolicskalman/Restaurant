package bme.hw.address;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.entities.Address;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressRepository addressRepository;
    private final AuthUserRepository authUserRepository;


    public AddressController(AddressRepository addressRepository, AuthUserRepository authUserRepository) {
        this.addressRepository = addressRepository;
        this.authUserRepository = authUserRepository;
    }


    @PostMapping("/authUser/addAddress")
    public void addNewAddress(@RequestBody AddressDTO addressDTO){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new RuntimeException("Logged user not present in database"));
        Address address = new Address();
        address.setAddress(addressDTO.getAddress());
        address.setCustomer(loggedInUser);
        addressRepository.save(address);
    }
}
