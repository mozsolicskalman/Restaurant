package bme.hw.meal;

import bme.hw.entities.Meal;
import lombok.Getter;

@Getter
public class FindAllMealsResponseDTO {
    private final Long id;
    private final String name;
    private final Long price;

    public FindAllMealsResponseDTO(Meal meal) {
        this.id = meal.getId();
        this.name = meal.getName();
        this.price = meal.getPrice();
    }
}
