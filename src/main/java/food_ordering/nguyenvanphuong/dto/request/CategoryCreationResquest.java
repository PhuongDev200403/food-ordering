package food_ordering.nguyenvanphuong.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCreationResquest {
    String name;
    Long restaurantId;
}
