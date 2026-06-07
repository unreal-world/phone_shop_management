package com.mycompany.phonestore.dao;

import java.util.List;

import com.mycompany.phonestore.model.User;

public interface UserDao {
    List<User> getAllUsers();
    User getUserById(String userID);
    User getUserByUsername(String username);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(String userID);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
