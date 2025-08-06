package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.dto.request.RestaurantCreationRequest;
import food_ordering.nguyenvanphuong.dto.request.RestaurantDto;
import food_ordering.nguyenvanphuong.entity.Address;
import food_ordering.nguyenvanphuong.entity.Restaurant;
import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.exception.RestaurantNotFoundException;
import food_ordering.nguyenvanphuong.repository.AddressRepository;
import food_ordering.nguyenvanphuong.repository.RestaurantRepository;
import food_ordering.nguyenvanphuong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class RestaurantServiceImp implements RestaurantService{
    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(RestaurantCreationRequest request, User user) {
        Address address = addressRepository.save(request.getAddress());

        Restaurant restaurant = Restaurant.builder()
                .address(address)
                .contactInformation(request.getContactInformation())
                .cuisineType(request.getCuisineType())
                .images(request.getImages())
                .openingHours(request.getOpeningHours())
                .name(request.getName())
                .description(request.getDescription())
                .registrationDate(LocalDateTime.now())
                .owner(user)
                .build();

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, RestaurantCreationRequest request) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        //kiểm tra xem có tồn tại không
        if(restaurant == null){
            throw new Exception("Restaurant not found");
        }
        //check xem cuisine có null không
        if(request.getCuisineType() != null){
            restaurant.setCuisineType(request.getCuisineType());
        }

        if(request.getName() != null){
            restaurant.setName(request.getName());
        }

        if(request.getDescription() != null){
            restaurant.setDescription(request.getDescription());
        }

        if(request.getContactInformation() != null){
            restaurant.setContactInformation(request.getContactInformation());
        }

        if(request.getAddress() != null){
            restaurant.setAddress(request.getAddress());
            addressRepository.save(request.getAddress());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        //kiểm tra xem có tồn tại không
        if(restaurant == null){
            throw new Exception("Restaurant not found");
        }
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {

        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        }
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId));
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwner(userId);
        if(restaurant == null){
            throw  new Exception("Restaurant not found with user id"+ userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto dto = RestaurantDto.builder()
                .description(restaurant.getDescription())
                .title(restaurant.getName())
                .images(restaurant.getImages())
                .id(restaurantId)
                .build();

        boolean isFavorite = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for (RestaurantDto favorite:favorites){
            if ((favorite.getId().equals(restaurantId))){
                isFavorite = true;
                break;
            }
        }

        if(user.getFavorites().contains(dto)){
            user.getFavorites().remove(dto);
        }else{
            user.getFavorites().add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);

        if(restaurant == null){
            throw new Exception("Restaurant not found");
        }
        restaurant.setOpen(!restaurant.isOpen());
        restaurantRepository.save(restaurant);
        return restaurant;
    }
}
