package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.dto.request.OrdercreationRequest;
import food_ordering.nguyenvanphuong.entity.*;
import food_ordering.nguyenvanphuong.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService{
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    CartService cartService;
    //Không ai được tạo đơn hang hết chi khi click nút thanh toán thi đơn hàng được tạo
    @Override
    public Order createOrder(OrdercreationRequest request, User user) throws Exception {
        Address shippingAddress = request.getDeliveryAddress();

        Address savedAddress = addressRepository.save(shippingAddress);

        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());

        Order order = Order.builder()
                .customer(user)
                .createAt(new Date())
                .orderStatus("PENDING")
                .deliveryAddress(savedAddress)
                .restaurant(restaurant)
                .build();


        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem cartItem:cart.getItems()){
            OrderItem orderItem = OrderItem.builder()
                    .food(cartItem.getFood())
                    .ingredients(cartItem.getIngredients())
                    .quantity(cartItem.getQuantity())
                    .totalPrice(cartItem.getTotalPrice())
                    .build();

            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }

        float totalPrice = cartService.calculateCartTotals(cart);

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        restaurant.getOrders().add(savedOrder);


        return order;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);

        if(orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")
        ){
            order.setOrderStatus(orderStatus);

            return orderRepository.save(order);
        }
        throw new Exception("Please select a valid order status");
    }

    //Hủy đơn hàng (ADMIN hủy đc tất cả đơn hàng
    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        if(order == null){
            throw new Exception("Order not found");
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUserOrders(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }


    //Lấy danh sách tất cả đơn hàng theo id của nhà hàng (ADMIN và RESTAURANT_OWNER)
    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {
        var orders = orderRepository.findByRestaurantId(restaurantId);

        if(orderStatus != null){
            orders = orders.stream().filter(order -> order
                    .getOrderStatus().equals(orderStatus)).toList();
        }
        return  orders;
    }

    @Override
    //Lấy đơn hàng theo id(ADMIN mới làm được còn customer chỉ lấy đc đơn hang của mình mua)
    public Order findOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));
    }

    @Override
    //Lấy tất cả order trong database (ADMIN mới lam được)
    public List<Order> getAllOrder() throws Exception {
        return orderRepository.findAll();
    }

}
