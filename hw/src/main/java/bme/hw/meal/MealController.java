package bme.hw.meal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/meals")
public class MealController {
    private final MealRepository mealRepository;

    public MealController(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok().body(mealRepository.findAll().stream().map(FindAllMealsResponseDTO::new).collect(Collectors.toList()));
    }

}
