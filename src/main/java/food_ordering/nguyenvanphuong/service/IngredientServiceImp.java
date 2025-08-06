package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.entity.IngredientCategory;
import food_ordering.nguyenvanphuong.entity.IngredientsItem;
import food_ordering.nguyenvanphuong.entity.Restaurant;
import food_ordering.nguyenvanphuong.repository.IngredientCategoryRepository;
import food_ordering.nguyenvanphuong.repository.IngredientsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImp implements IngredientsService{
    @Autowired
    IngredientsItemRepository ingredientsItemRepository;

    @Autowired
    IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory ingredientCategory = IngredientCategory.builder()
                .name(name)
                .restaurant(restaurant)
                .build();

        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {

        return ingredientCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception("IngredientCategory not found"));
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        //Restaurant restaurant = restaurantService.findRestaurantById(id);

        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngrendients(Long restaurantId) throws Exception {
        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = findIngredientCategoryById(categoryId);

        IngredientsItem ingredientsItem = IngredientsItem.builder()
                .name(ingredientName)
                .restaurant(restaurant)
                .category(ingredientCategory)
                .build();

        ingredientCategory.getIngredients().add(ingredientsItemRepository.save(ingredientsItem));
        return ingredientsItem;
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        IngredientsItem ingredientsItem = ingredientsItemRepository.findById(id)
                .orElseThrow(() -> new Exception("IngredientsItem not found"));

        ingredientsItem.setInStock(!ingredientsItem.isInStock());
        return ingredientsItemRepository.save(ingredientsItem);
    }
}
