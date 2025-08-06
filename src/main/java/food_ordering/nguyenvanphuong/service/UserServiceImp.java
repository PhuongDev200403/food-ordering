package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.config.JwtProvider;
import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User findUserByJwtToken(String token) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(token);

        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if(user != null){
            throw new Exception("User not found");
        }

        return user;
    }

    @Override
    public List<User> getAllUser() throws Exception {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User request) throws Exception {
        User isEmailExist = userRepository.findByEmail(request.getEmail());
        if(isEmailExist != null){
            throw new Exception("Email đã tồn tại");
        }

        // Tạo user mới
        User createdUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        return userRepository.save(createdUser);
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Không tìm thấy user"));

        userRepository.delete(user);
    }

    @Override
    public User updateUser(Long id, User request) throws Exception {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Không tìm thấy user"));

        // Kiểm tra email đã tồn tại (trừ user hiện tại)
        if (!existingUser.getEmail().equals(request.getEmail())) {
            User isEmailExist = userRepository.findByEmail(request.getEmail());
            if(isEmailExist != null){
                throw new Exception("Email đã tồn tại");
            }
        }

        // Cập nhật thông tin
        existingUser.setEmail(request.getEmail());
        existingUser.setFullName(request.getFullName());
        existingUser.setRole(request.getRole());

        // Chỉ cập nhật password nếu có
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(existingUser);
    }
}
