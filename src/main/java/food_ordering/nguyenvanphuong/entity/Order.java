package food_ordering.nguyenvanphuong.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    User customer;

    @ManyToOne
    @JsonIgnore
    Restaurant restaurant;

    Long totalAmount;

    @JsonProperty("order_status")
    String orderStatus;

    @JsonProperty("create_at")
    Date createAt;

    @ManyToOne
    @JsonProperty("delivery_address")
    Address deliveryAddress;

    @OneToMany
    List<OrderItem> items = new ArrayList<>();

    @JsonProperty("total_items")
    int totalItems;

    @JsonProperty("total_price")
    float totalPrice;
}
