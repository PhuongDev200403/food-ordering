package food_ordering.nguyenvanphuong.repository;

import food_ordering.nguyenvanphuong.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
