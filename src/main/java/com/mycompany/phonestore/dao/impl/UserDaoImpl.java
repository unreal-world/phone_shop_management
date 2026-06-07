package com.mycompany.phonestore.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.phonestore.dao.UserDao;
import com.mycompany.phonestore.model.User;
import com.mycompany.phonestore.model.UserRole;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM User";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                rs.getString("userID"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("fullName"),
                rs.getString("email"),
                rs.getString("phoneNumber"),
                UserRole.valueOf(rs.getString("role"))
        ));
    }

    @Override
    public User getUserById(String userID) {
        String sql = "SELECT * FROM User WHERE userID = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{userID}, (rs, rowNum) -> new User(
                rs.getString("userID"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("fullName"),
                rs.getString("email"),
                rs.getString("phoneNumber"),
                UserRole.valueOf(rs.getString("role"))
        ));
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{username}, (rs, rowNum) -> new User(
                rs.getString("userID"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("fullName"),
                rs.getString("email"),
                rs.getString("phoneNumber"),
                UserRole.valueOf(rs.getString("role"))
        ));
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO User (userID, username, password, fullName, email, phoneNumber, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserID(), user.getUsername(), user.getPassword(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getRole().toString());
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE User SET username=?, password=?, fullName=?, email=?, phoneNumber=?, role=? WHERE userID=?";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getRole().toString(), user.getUserID());
    }

    @Override
    public void deleteUser(String userID) {
        String sql = "DELETE FROM User WHERE userID=?";
        jdbcTemplate.update(sql, userID);
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM User WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM User WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
