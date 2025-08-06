package food_ordering.nguyenvanphuong.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import food_ordering.nguyenvanphuong.dto.request.RestaurantDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fullName;

    String password;

    String email;

    USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    List<Order> orders = new ArrayList<>();

    @ElementCollection
    List<RestaurantDto> favorites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Address> addresses = new ArrayList<>();

}
