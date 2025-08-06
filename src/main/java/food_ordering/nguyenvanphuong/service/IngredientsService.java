package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.entity.IngredientCategory;
import food_ordering.nguyenvanphuong.entity.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    public List<IngredientsItem> findRestaurantsIngrendients(Long restaurantId) throws Exception;

    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    public IngredientsItem updateStock(Long id) throws Exception;
}
