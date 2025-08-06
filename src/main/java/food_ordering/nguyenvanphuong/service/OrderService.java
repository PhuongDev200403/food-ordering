package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.dto.request.OrdercreationRequest;
import food_ordering.nguyenvanphuong.entity.Order;
import food_ordering.nguyenvanphuong.entity.User;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrdercreationRequest request, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUserOrders(Long userId) throws Exception;

    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;

    public List<Order> getAllOrder() throws Exception;
}
