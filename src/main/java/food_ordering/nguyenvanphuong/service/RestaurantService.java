package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.dto.request.RestaurantCreationRequest;
import food_ordering.nguyenvanphuong.dto.request.RestaurantDto;
import food_ordering.nguyenvanphuong.entity.Restaurant;
import food_ordering.nguyenvanphuong.entity.User;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(RestaurantCreationRequest request, User user);

    public Restaurant updateRestaurant(Long restaurantId, RestaurantCreationRequest request) throws Exception;

    public  void deleteRestaurant(Long restaurantId) throws  Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long restaurantId) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
