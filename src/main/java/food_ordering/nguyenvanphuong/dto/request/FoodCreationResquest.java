package food_ordering.nguyenvanphuong.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import food_ordering.nguyenvanphuong.entity.Category;
import food_ordering.nguyenvanphuong.entity.IngredientsItem;
import food_ordering.nguyenvanphuong.entity.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodCreationResquest {
    String name;

    String description;

    Long price;

    Category category;

    List<String> images;

    boolean available;

    Long restaurantId;

    boolean isVegetarian;

    boolean isSeasonal;

    List<IngredientsItem> ingredients = new ArrayList<>();

    Date creationDate;
}
