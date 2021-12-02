package bme.hw.meal;

import bme.hw.base.auth.Role;
import bme.hw.base.auth.RoleSecured;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/meals")
public class MealController {
    private final MealRepository mealRepository;

    public MealController(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @GetMapping
    @RoleSecured({ Role.ROLE_CUSTOMER })
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok().body(mealRepository.findAll().stream().map(FindAllMealsResponseDTO::new).collect(Collectors.toList()));
    }
}
