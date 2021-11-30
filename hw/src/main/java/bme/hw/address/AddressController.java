package bme.hw.address;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.entities.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressRepository addressRepository;
    private final AuthUserRepository authUserRepository;

    public AddressController(AddressRepository addressRepository, AuthUserRepository authUserRepository) {
        this.addressRepository = addressRepository;
        this.authUserRepository = authUserRepository;
    }

    @PostMapping
    public Long addNewAddress(@RequestBody AddressRequestDTO addressDTO) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                        () -> new RuntimeException("Logged user not present in database"));
        Address address = new Address();
        address.setAddress(addressDTO.getAddress());
        address.setCustomer(loggedInUser);
        return addressRepository.saveAndFlush(address).getId();
    }

    @GetMapping
    public ResponseEntity<Object> getAddresses() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                        () -> new RuntimeException("Logged user not present in database"));
        List<Address> addresses = addressRepository.findByCustomer_Id(loggedInUser.getId());
        if (addresses.isEmpty())
            return null;
        return ResponseEntity.ok().body(addresses.stream().map(AddressResponseDTO::new).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAddress(@PathVariable("id") Long id){
        if(id!=null)
            addressRepository.deleteById(id);
    }
}
