package food_ordering.nguyenvanphuong.controller;

import food_ordering.nguyenvanphuong.dto.request.RestaurantDto;
import food_ordering.nguyenvanphuong.entity.Restaurant;
import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.service.RestaurantService;
import food_ordering.nguyenvanphuong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;

    //tìm kiếm theo từ khóa
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(
            @RequestHeader("Authorization") String token,
            @RequestParam String keyword
    )throws Exception {
        User user = userService.findUserByJwtToken(token);

        var restaurants = restaurantService.searchRestaurant(keyword);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    //Lấy tất cả nhà hàng
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants(
            @RequestHeader("Authorization") String token
    )throws Exception {
        User user = userService.findUserByJwtToken(token);

        var restaurants = restaurantService.getAllRestaurant();

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    //lấy restaurant theo id
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(token);

        var restaurant = restaurantService.findRestaurantById(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    //Thêm vào danh sách yêu thích
    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addToFavorite(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long id
    )throws Exception {
        User user = userService.findUserByJwtToken(token);

        RestaurantDto restaurant = restaurantService.addToFavorites(id, user);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    //tìm theo user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            //@RequestBody RestaurantCreationRequest request,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserByJwtToken(token);


        var restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
