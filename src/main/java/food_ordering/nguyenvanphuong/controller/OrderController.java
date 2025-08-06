package food_ordering.nguyenvanphuong.controller;

import food_ordering.nguyenvanphuong.dto.request.OrdercreationRequest;
import food_ordering.nguyenvanphuong.entity.Order;
import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.service.OrderService;
import food_ordering.nguyenvanphuong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody OrdercreationRequest request,
                                             @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);

        Order order = orderService.createOrder(request, user);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/orders/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);

        List<Order> orders = orderService.getUserOrders(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
