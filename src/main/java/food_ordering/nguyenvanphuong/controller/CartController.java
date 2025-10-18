package food_ordering.nguyenvanphuong.controller;

import food_ordering.nguyenvanphuong.dto.request.AddCartItemRequest;
import food_ordering.nguyenvanphuong.dto.request.UpdateCartItemRequest;
import food_ordering.nguyenvanphuong.entity.Cart;
import food_ordering.nguyenvanphuong.entity.CartItem;
import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.service.CartService;
import food_ordering.nguyenvanphuong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    //Thêm sản phẩm vào giỏ hàng
    @PostMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestBody AddCartItemRequest request,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        CartItem cartItem = cartService.addItemToCart(request, token);

        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    //Cập nhật số lượng sản phẩm trong giỏ hàng
    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @RequestBody UpdateCartItemRequest request,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    //Xóa item khỏi giỏ hàng
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/cart-item/{id}/remove")
    public  ResponseEntity<Cart> deletecartItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        Cart cart = cartService.removeCartItemFromCart(id, token);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    //Clear giỏ hàng
    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Cart cart = cartService.clearCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    //dùng để lây giỏ hàng của chính mình
    @GetMapping("/cart")
    public ResponseEntity<Cart> findCartByUserId(
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Cart cart = cartService.findCartByUserId(user.getId()); // <-- Đúng!
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    //xem chi tiết giỏ hàng
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/carts/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(cartService.findCartById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin/carts")
    public ResponseEntity<List<Cart>> getAllCarts(){
        return new ResponseEntity<>(cartService.getAllCart(), HttpStatus.OK);
    }


}
