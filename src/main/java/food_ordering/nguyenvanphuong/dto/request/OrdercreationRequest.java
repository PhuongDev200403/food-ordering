package food_ordering.nguyenvanphuong.dto.request;

import food_ordering.nguyenvanphuong.entity.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdercreationRequest {
    Long restaurantId;
    Address deliveryAddress;
}
