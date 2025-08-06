package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.dto.request.AddCartItemRequest;
import food_ordering.nguyenvanphuong.entity.Cart;
import food_ordering.nguyenvanphuong.entity.CartItem;
import food_ordering.nguyenvanphuong.entity.Food;
import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.repository.CartItemRepository;
import food_ordering.nguyenvanphuong.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImp implements CartService{
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    UserService userService;

    @Autowired
    FoodService foodService;



    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Food food = foodService.findFoodById(request.getFoodId());

        // Kiểm tra cart có tồn tại không, nếu không thì tạo mới
        Cart cart = cartRepository.findByCustomerId(user.getId());
        if (cart == null) {
            // Tạo cart mới cho user

            cart = Cart.builder()
                    .customer(user)
                    .build();
            cartRepository.save(cart);
        }

        // Kiểm tra xem food đã có trong cart chưa
        for (CartItem cartItem: cart.getItems()){
            if(cartItem.getFood().equals(food)){
                int newquantity = cartItem.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newquantity);
            }
        }

        // Tạo cart item mới
        CartItem newCartItem = CartItem.builder()
                .food(food)
                .cart(cart)
                .quantity(request.getQuantity())
                .ingredients(request.getIngredients())
                .totalPrice(request.getQuantity() * food.getPrice())
                .build();

        CartItem savedCartItem = cartItemRepository.save(newCartItem);

        cart.getItems().add(savedCartItem);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception("Cart items not found"));

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeCartItemFromCart(Long cartItem, String token) throws Exception {
        User user = userService.findUserByJwtToken(token);

        Cart cart = cartRepository.findByCustomerId(user.getId());

        CartItem item = cartItemRepository.findById(cartItem)
                .orElseThrow(() -> new Exception("Cart items not found"));

        cart.getItems().remove(item);
        cart.setTotal(calculateCartTotals(cart));
        return cartRepository.save(cart);
    }

    @Override
    public float calculateCartTotals(Cart cart) throws Exception {
        float total = 0L;
        for (CartItem item : cart.getItems()) {
            float price = item.getFood().getPrice();
            int quantity = item.getQuantity();
            System.out.println("CartItem id: " + item.getId() + ", price: " + price + ", quantity: " + quantity);
            if (Float.isNaN(price) || Float.isNaN(quantity)) {
                System.out.println("LỖI: Giá trị NaN!");
                continue;
            }
            total += price * quantity;
        }
        System.out.println("Tổng tính được: " + total);
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        return cartRepository.findById(id)
                .orElseThrow(() -> new Exception("Cart not found"));
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {

        Cart cart = cartRepository.findByCustomerId(userId);

        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {

        Cart cart = cartRepository.findByCustomerId(userId);
        cart.getItems().clear();
        cart.setTotal(0);
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }
}
