package food_ordering.nguyenvanphuong.service;

import food_ordering.nguyenvanphuong.entity.User;

import java.util.List;

public interface UserService {
    public User findUserByJwtToken(String token) throws Exception;

    public User findUserByEmail(String email) throws Exception;

    public List<User> getAllUser() throws Exception;

    public User createUser(User request) throws Exception;

    public void deleteUser(Long id) throws Exception;

    public User updateUser(Long id, User request) throws Exception;

}
