package bme.hw.coupon;

import bme.hw.auth_user.AuthUser;
import bme.hw.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findAllByAuthUserAndOrderIsNull(AuthUser customer);
}
