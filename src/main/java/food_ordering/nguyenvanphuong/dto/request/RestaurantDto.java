package food_ordering.nguyenvanphuong.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class RestaurantDto {

    String title;

    @Column(length = 1000)
    List<String> images;

    String description;

    Long id;
}
