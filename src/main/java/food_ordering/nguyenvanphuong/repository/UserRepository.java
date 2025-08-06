package food_ordering.nguyenvanphuong.repository;

import food_ordering.nguyenvanphuong.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);
}
