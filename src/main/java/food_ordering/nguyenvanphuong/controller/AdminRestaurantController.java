package food_ordering.nguyenvanphuong.controller;

import food_ordering.nguyenvanphuong.dto.request.RestaurantCreationRequest;
import food_ordering.nguyenvanphuong.entity.Restaurant;
import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.service.RestaurantService;
import food_ordering.nguyenvanphuong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;

    //Tạo mới
    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody RestaurantCreationRequest request,
            @RequestHeader("Authorization") String token
    )throws Exception{
        User user = userService.findUserByJwtToken(token);

        Restaurant restaurant = restaurantService.createRestaurant(request, user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    //Cập nhật
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody RestaurantCreationRequest request,
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    )throws Exception{
        User user = userService.findUserByJwtToken(token);

        Restaurant restaurant = restaurantService.updateRestaurant(id, request);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    //Xóa
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(
            @RequestBody RestaurantCreationRequest request,
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(token);

        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
    }
    //Cập nhật trạng thái nhà hàng
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateStatus(
            //@RequestBody RestaurantCreationRequest request,
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(token);

        var restaurant = restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


}
