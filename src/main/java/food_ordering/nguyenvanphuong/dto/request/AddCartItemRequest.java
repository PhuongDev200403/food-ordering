package food_ordering.nguyenvanphuong.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCartItemRequest {
    Long foodId;

    int quantity;

    List<String> ingredients;
}
