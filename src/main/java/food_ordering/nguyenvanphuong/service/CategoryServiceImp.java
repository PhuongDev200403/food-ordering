package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.dto.request.CategoryCreationResquest;
import food_ordering.nguyenvanphuong.entity.Category;
import food_ordering.nguyenvanphuong.entity.Food;
import food_ordering.nguyenvanphuong.entity.Restaurant;
import food_ordering.nguyenvanphuong.repository.CategoryRepository;
import food_ordering.nguyenvanphuong.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    FoodRepository foodRepository;

    @Override
    public Category createCategory(CategoryCreationResquest resquest) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(resquest.getRestaurantId());

        Category category = Category.builder()
                .name(resquest.getName())
                .restaurant(restaurant)
                .build();

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        return categoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));
    }

    @Override
    public List<Category> findAllCategory() throws Exception {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategoryById(Long id) throws Exception {
        // Kiểm tra category có tồn tại không
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Không tìm thấy danh mục với ID: " + id));

        // Sau đó xóa category
        categoryRepository.deleteById(id);
    }
}
