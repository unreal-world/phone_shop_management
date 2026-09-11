package com.mycompany.phonestore.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mycompany.phonestore.dao.PasswordResetTokenDao;
import com.mycompany.phonestore.dao.UserDao;
import com.mycompany.phonestore.model.PasswordResetToken;
import com.mycompany.phonestore.model.User;
import com.mycompany.phonestore.model.UserRole;
import com.mycompany.phonestore.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordResetTokenDao passwordResetTokenDao;
    private final JavaMailSender mailSender;

    public UserServiceImpl(UserDao userDao,
                           PasswordResetTokenDao passwordResetTokenDao,
                           JavaMailSender mailSender) {
        this.userDao = userDao;
        this.passwordResetTokenDao = passwordResetTokenDao;
        this.mailSender = mailSender;
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
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
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

    @Override
    public String createPasswordResetToken(String email) {
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            return null;
        }
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(
                UUID.randomUUID().toString(),
                token,
                user.getUserID(),
                LocalDateTime.now().plusMinutes(15),
                false
        );
        passwordResetTokenDao.save(resetToken);

        // Gửi email chứa link đặt lại mật khẩu
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("huy20042504@gmail.com");
        message.setTo(email);
        message.setSubject("[Phone Store] Đặt lại mật khẩu");
        message.setText(
                "Xin chào " + user.getFullName() + ",\n\n" +
                "Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.\n\n" +
                "Nhấn vào link sau để đặt lại mật khẩu (có hiệu lực trong 15 phút):\n" +
                "http://localhost:8080/phonestore/auth/reset-password?token=" + token + "\n\n" +
                "Nếu bạn không yêu cầu, hãy bỏ qua email này.\n\n" +
                "Trân trọng,\nPhone Store"
        );
        mailSender.send(message);

        return token;
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenDao.findByToken(token);
        if (resetToken == null || resetToken.isUsed() || resetToken.isExpired()) {
            return null;
        }
        return resetToken.getUserID();
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenDao.findByToken(token);
        if (resetToken == null) {
            return;
        }
        User user = userDao.getUserById(resetToken.getUserID());
        if (user != null) {
            user.setPassword(newPassword);
            userDao.updateUser(user);
            passwordResetTokenDao.markUsed(token);
        }
    }
}
