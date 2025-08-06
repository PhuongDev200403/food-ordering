package food_ordering.nguyenvanphuong.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IngredientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @JsonIgnore
    @ManyToOne
    Restaurant restaurant;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    List<IngredientsItem> ingredients = new ArrayList<>();
}
