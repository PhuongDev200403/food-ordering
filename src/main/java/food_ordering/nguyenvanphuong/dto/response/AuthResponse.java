package food_ordering.nguyenvanphuong.dto.response;

import food_ordering.nguyenvanphuong.entity.USER_ROLE;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {
    String token;
    String message;
    USER_ROLE role;
}
