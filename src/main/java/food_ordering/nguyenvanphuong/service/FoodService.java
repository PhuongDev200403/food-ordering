package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.dto.request.FoodCreationResquest;
import food_ordering.nguyenvanphuong.entity.Category;
import food_ordering.nguyenvanphuong.entity.Food;
import food_ordering.nguyenvanphuong.entity.Restaurant;

import java.util.List;

public interface FoodService {
    public Food createFood(FoodCreationResquest resquest, Category category, Restaurant restaurant);

    public void deleteFood(Long foodId) throws Exception;

    public List<Food> getAllFood() throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegetarian,
                                         boolean isNonVeg,
                                         boolean isSeasonal,
                                         String foodCategory
    );

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailabilityStatus(Long foodId) throws Exception;
}
