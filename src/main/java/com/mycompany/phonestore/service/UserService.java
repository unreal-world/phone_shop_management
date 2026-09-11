package com.mycompany.phonestore.service;

import java.util.List;

import com.mycompany.phonestore.model.User;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(String userID);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(String userID);
    User authenticate(String username, String password);
    void register(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    // Quên mật khẩu
    String createPasswordResetToken(String email);
    String validatePasswordResetToken(String token);
    void resetPassword(String token, String newPassword);
}
