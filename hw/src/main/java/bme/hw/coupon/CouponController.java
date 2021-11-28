package bme.hw.coupon;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.entities.Coupon;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {
    private final AuthUserRepository authUserRepository;
    private final CouponRepository couponRepository;

    public CouponController(AuthUserRepository authUserRepository, CouponRepository couponRepository) {
        this.authUserRepository = authUserRepository;
        this.couponRepository = couponRepository;
    }

    @GetMapping
    public List <ResponseCouponDTO> getCoupons(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                () -> new RuntimeException("Logged user not present in database"));
        List<Coupon> coupons = couponRepository.findByCustomer_Id(loggedInUser.getId());
        if(coupons.isEmpty())
            return null;
        List<ResponseCouponDTO> couponDTOS = new ArrayList<>();
        coupons.forEach(c -> couponDTOS.add(new ResponseCouponDTO(c.getPercentage())));
        return couponDTOS;
    }
}
