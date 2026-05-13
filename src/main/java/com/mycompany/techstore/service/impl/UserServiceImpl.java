package com.mycompany.techstore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mycompany.techstore.dao.UserDao;
import com.mycompany.techstore.model.User;
import com.mycompany.techstore.service.UserService;

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
}
