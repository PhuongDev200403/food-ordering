package food_ordering.nguyenvanphuong.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String description;

    float price;

    @JsonProperty("food_category")
    @ManyToOne
    Category foodCategory;

    @ElementCollection
    @Column(length = 1000)
    List<String> images = new ArrayList<>();

    boolean available;

    @ManyToOne
    Restaurant restaurant;

    @JsonProperty("is_vegetarian")
    boolean isVegetarian;

    @JsonProperty("is_seasonal")
    boolean isSeasonal;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "food_id")
    private List<IngredientsItem> ingredients = new ArrayList<>();

    @JsonProperty("create_date")
    Date creationDate;
}
