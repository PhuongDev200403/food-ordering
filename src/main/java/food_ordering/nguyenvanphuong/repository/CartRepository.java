package food_ordering.nguyenvanphuong.repository;

import food_ordering.nguyenvanphuong.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCustomerId(Long userId);
}
