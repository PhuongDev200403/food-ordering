package food_ordering.nguyenvanphuong.controller;

import food_ordering.nguyenvanphuong.dto.request.FoodCreationResquest;
import food_ordering.nguyenvanphuong.entity.Food;
import food_ordering.nguyenvanphuong.entity.Restaurant;
import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.service.FoodService;
import food_ordering.nguyenvanphuong.service.RestaurantService;
import food_ordering.nguyenvanphuong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/foods")
public class AdminFoodController {

    @Autowired
    FoodService foodService;

    @Autowired
    UserService userService;

    @Autowired
    RestaurantService restaurantService;
    @PostMapping()
    public ResponseEntity<Food> createFood(@RequestBody FoodCreationResquest resquest,
                                           @RequestHeader("Authorization") String token
    )throws Exception{
        User user = userService.findUserByJwtToken(token);
        Restaurant restaurant = restaurantService.findRestaurantById(resquest.getRestaurantId());

        Food food = foodService.createFood(resquest, resquest.getCategory(), restaurant);
         return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id,
                                             @RequestHeader("Authorization") String token
    ) throws Exception{
        User user = userService.findUserByJwtToken(token);

        foodService.deleteFood(id);
        return new ResponseEntity<>("Delete food successfully", HttpStatus.OK);
    }

    //cập nhật status cho food
    @PutMapping("/{id}/status")
    public ResponseEntity<Food> updateFoodStatus(@PathVariable Long id,
                                                 @RequestHeader("Authorization") String token
    )throws Exception{
        User user = userService.findUserByJwtToken(token);

        Food food = foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

}
