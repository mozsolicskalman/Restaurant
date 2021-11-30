package bme.hw.coupon;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.entities.Coupon;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {
    private final AuthUserRepository authUserRepository;
    private final CouponRepository couponRepository;

    public CouponController(AuthUserRepository authUserRepository, CouponRepository couponRepository) {
        this.authUserRepository = authUserRepository;
        this.couponRepository = couponRepository;
    }

    @GetMapping
    public ResponseEntity<Object> getCoupons(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new RuntimeException("Logged user not present in database"));
        List<Coupon> coupons = couponRepository.findAllByAuthUserAndOrderIsNull(loggedInUser);
        if(coupons.isEmpty())
            return null;
        return ResponseEntity.ok().body(coupons.stream().map(ResponseCouponDTO::new).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAddress(@PathVariable("id") Long id){
        if(id!=null)
            couponRepository.deleteById(id);
    }
}
