package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.dto.request.FoodCreationResquest;
import food_ordering.nguyenvanphuong.entity.Category;
import food_ordering.nguyenvanphuong.entity.Food;
import food_ordering.nguyenvanphuong.entity.Restaurant;
import food_ordering.nguyenvanphuong.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FoodServiceImp implements FoodService{
    @Autowired
    FoodRepository foodRepository;

    @Override
    public Food createFood(FoodCreationResquest resquest, Category category, Restaurant restaurant) {
        if (resquest == null || category == null || restaurant == null) {
            throw new IllegalArgumentException("Thiếu thông tin tạo món ăn");
        }

        if (resquest.getPrice() <= 0) {
            throw new IllegalArgumentException("Giá món ăn phải lớn hơn 0");
        }

        Food food = Food.builder()
                .foodCategory(category)
                .restaurant(restaurant)
                .description(resquest.getDescription())
                .ingredients(resquest.getIngredients())
                .images(resquest.getImages() != null ? resquest.getImages() : new ArrayList<>())
                .isVegetarian(resquest.isVegetarian())
                .isSeasonal(resquest.isSeasonal())
                .name(resquest.getName())
                .price(resquest.getPrice())
                .build();

        return foodRepository.save(food);
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getAllFood() throws Exception {
        return foodRepository.findAll();
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegetarian,
                                         boolean isNonVeg,
                                         boolean isSeasonal,
                                         String foodCategory) {

        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        return foods.stream()
                .filter(food -> !isVegetarian || food.isVegetarian())
                .filter(food -> !isNonVeg || !food.isVegetarian())
                .filter(food -> !isSeasonal || food.isSeasonal())
                .filter(food -> foodCategory == null || foodCategory.isBlank()
                        || Objects.equals(food.getFoodCategory().getName(), foodCategory))
                .toList();
    }
    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> food = foodRepository.findById(foodId);

        if(food.isEmpty()){
            throw new Exception("Food not existed");
        }
        return food.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
