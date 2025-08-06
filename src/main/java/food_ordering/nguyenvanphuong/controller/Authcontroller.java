package food_ordering.nguyenvanphuong.controller;

import food_ordering.nguyenvanphuong.config.JwtProvider;
import food_ordering.nguyenvanphuong.dto.request.LoginRequest;
import food_ordering.nguyenvanphuong.dto.response.AuthResponse;
import food_ordering.nguyenvanphuong.entity.Cart;
import food_ordering.nguyenvanphuong.entity.USER_ROLE;
import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.repository.CartRepository;
import food_ordering.nguyenvanphuong.repository.UserRepository;
import food_ordering.nguyenvanphuong.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class Authcontroller {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    CartRepository cartRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUser(@RequestBody User request) throws Exception {
        User isEmailExist = userRepository.findByEmail(request.getEmail());
        if(isEmailExist != null){
            throw new Exception("Email existed");
        }

        User createdUser = new User();
        createdUser.setEmail(request.getEmail());
        createdUser.setFullName(request.getFullName());
        createdUser.setPassword(passwordEncoder.encode(request.getPassword()));
        createdUser.setRole(request.getRole());

        User savedUser = userRepository.save(createdUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        String token = jwtProvider.generateToken(authentication);

        //USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setMessage("Register successfully");
        authResponse.setRole(savedUser.getRole());
        //authResponse.setRole(role);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody LoginRequest request) {
        String username = request.getEmail();
        String password = request.getPassword();

        try {
            // Xác thực user
            Authentication authentication = customerUserDetailsService.authenticate(username, password);

            // Lấy role
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

            // Sinh JWT
            String token = jwtProvider.generateToken(authentication);

            // Tạo response trả về
            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(token);
            authResponse.setMessage("Login successfully");
            authResponse.setRole(USER_ROLE.valueOf(role));

            return new ResponseEntity<>(authResponse, HttpStatus.OK);

        } catch (BadCredentialsException ex) {
            // Nếu sai tài khoản hoặc password
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (Exception ex) {
            // Các lỗi khác
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + ex.getMessage());
        }
    }
}
