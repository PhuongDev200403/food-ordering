package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.dto.request.CategoryCreationResquest;
import food_ordering.nguyenvanphuong.entity.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(CategoryCreationResquest resquest) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long id) throws Exception;

    public Category findCategoryById(Long id) throws Exception;

    public List<Category> findAllCategory() throws Exception;

    public void deleteCategoryById(Long id) throws Exception;
}
