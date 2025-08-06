package food_ordering.nguyenvanphuong.controller;

import food_ordering.nguyenvanphuong.entity.Food;
import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.service.FoodService;
import food_ordering.nguyenvanphuong.service.RestaurantService;
import food_ordering.nguyenvanphuong.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {
    @Autowired
    UserService userService;

    @Autowired
    FoodService foodService;

    @Autowired
    RestaurantService restaurantService;

    //Search theo keyword
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestHeader("Authorization") String token,
                                     @RequestParam String keyword
    )throws Exception{
        User user = userService.findUserByJwtToken(token);

        var foods = foodService.searchFood(keyword);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    //Tìm kiếm food theo id
    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id,
                                            @RequestHeader("Authorization") String token
    )throws Exception{
        User user = userService.findUserByJwtToken(token);

        return new ResponseEntity<>(foodService.findFoodById(id), HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam boolean vegetarian,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonVeg,
            @RequestHeader("Authorization") String token,
            @PathVariable Long restaurantId,
            @RequestParam(required = false) String food_category
    ) throws Exception {
        User user = userService.findUserByJwtToken(token);

        List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegetarian, nonVeg, seasonal,food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Food>> getAllFood(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);

        return new ResponseEntity<>(foodService.getAllFood(), HttpStatus.OK);
    }
}
