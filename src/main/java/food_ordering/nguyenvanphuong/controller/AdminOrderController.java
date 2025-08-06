package food_ordering.nguyenvanphuong.controller;

import food_ordering.nguyenvanphuong.entity.Order;
import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.service.OrderService;
import food_ordering.nguyenvanphuong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminOrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;


    //Dành cho restaurant
    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderByRestaurant(
            @PathVariable Long id,
            @RequestParam(required = false) String orderStatus,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserByJwtToken(token);
        var orders = orderService.getRestaurantOrders(id, orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{id}/order_status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Order order = orderService.updateOrder(id, orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() throws Exception {
        return new ResponseEntity<>(orderService.getAllOrder(), HttpStatus.OK);
    }
}
