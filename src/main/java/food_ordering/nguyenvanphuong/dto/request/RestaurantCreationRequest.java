package food_ordering.nguyenvanphuong.dto.request;

import food_ordering.nguyenvanphuong.entity.Address;
import food_ordering.nguyenvanphuong.entity.ContactInformation;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantCreationRequest {
    String name;
    String description;
    String cuisineType;
    Address address;
    ContactInformation contactInformation;
    String openingHours;
    List<String> images;
}
