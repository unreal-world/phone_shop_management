package com.mycompany.techstore.service;

import java.util.List;

import com.mycompany.techstore.model.User;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(String userID);
    User getUserByUsername(String username);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(String userID);
    User authenticate(String username, String password);
    void register(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
