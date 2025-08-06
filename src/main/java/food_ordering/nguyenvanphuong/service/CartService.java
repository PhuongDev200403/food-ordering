package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.dto.request.AddCartItemRequest;
import food_ordering.nguyenvanphuong.entity.Cart;
import food_ordering.nguyenvanphuong.entity.CartItem;
import food_ordering.nguyenvanphuong.entity.User;

import java.util.List;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest request, String token) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeCartItemFromCart(Long cartItem, String token) throws Exception;

    public float calculateCartTotals(Cart cart) throws Exception;

    public Cart findCartById(Long id) throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    public Cart clearCart(Long userId)throws Exception;

    public List<Cart> getAllCart();

}
