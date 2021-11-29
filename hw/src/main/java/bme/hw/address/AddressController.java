package bme.hw.address;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.entities.Address;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public void addNewAddress(@RequestBody ResponseAddressDTO addressDTO){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new RuntimeException("Logged user not present in database"));
        Address address = new Address();
        address.setAddress(addressDTO.getAddress());
        address.setCustomer(loggedInUser);
        addressRepository.save(address);
    }

    @GetMapping
    public List<ResponseAddressDTO> getAddress(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new RuntimeException("Logged user not present in database"));
        List<Address> addresses = addressRepository.findByCustomer_Id(loggedInUser.getId());
        if(addresses.isEmpty())
            return null;
        List<ResponseAddressDTO> addressDTOS = new ArrayList<>();
        addresses.forEach(a -> addressDTOS.add(new ResponseAddressDTO(a.getAddress(),loggedInUser.getId())));
        return addressDTOS;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAddress(@PathVariable("id") Long id){
        if(id!=null)
            addressRepository.deleteById(id);
    }
}
