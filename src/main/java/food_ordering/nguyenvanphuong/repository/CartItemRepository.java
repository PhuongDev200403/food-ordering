package food_ordering.nguyenvanphuong.repository;

import food_ordering.nguyenvanphuong.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
