package food_ordering.nguyenvanphuong.controller;

import food_ordering.nguyenvanphuong.entity.User;
import food_ordering.nguyenvanphuong.repository.UserRepository;
import food_ordering.nguyenvanphuong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.SequencedSet;

@RestController

public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/users/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUser() throws Exception {

        var users = userService.getAllUser();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //Tạo mới một tài khoản chỉ dành cho admin
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users")
    public ResponseEntity<User> createUser(@RequestBody User request) throws Exception {

        User newUser = userService.createUser(request);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    //xóa tài khoản người dùng chỉ dành cho admin
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws Exception {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User request) throws Exception {
        return new ResponseEntity<>(userService.updateUser(id, request), HttpStatus.OK);
    }

}
