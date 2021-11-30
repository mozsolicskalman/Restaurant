package bme.hw.meal;

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
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok().body(mealRepository.findAll().stream().map(FindAllMealsResponseDTO::new).collect(Collectors.toList()));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAddress(@PathVariable("id") Long id){
        if(id!=null)
            mealRepository.deleteById(id);
    }

}
