package com.mycompany.phonestore.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mycompany.phonestore.dao.UserDao;
import com.mycompany.phonestore.model.User;
import com.mycompany.phonestore.model.UserRole;
import com.mycompany.phonestore.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(String userID) {
        return userDao.getUserById(userID);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(String userID) {
        userDao.deleteUser(userID);
    }

    @Override
    public User authenticate(String username, String password) {
        User user = userDao.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public void register(User user) {
        user.setUserID(UUID.randomUUID().toString());
        user.setRole(UserRole.CUSTOMER);
        userDao.addUser(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }
}
