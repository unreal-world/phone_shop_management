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
    private final org.springframework.core.env.Environment env;

    public UserServiceImpl(UserDao userDao,
                           PasswordResetTokenDao passwordResetTokenDao,
                           JavaMailSender mailSender,
                           org.springframework.core.env.Environment env) {
        this.userDao = userDao;
        this.passwordResetTokenDao = passwordResetTokenDao;
        this.mailSender = mailSender;
        this.env = env;
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

        // Gửi email chứa link đặt lại mật khẩu bằng MimeMessage (hỗ trợ UTF-8 và HTML)
        try {
            jakarta.mail.internet.MimeMessage mimeMessage = mailSender.createMimeMessage();
            org.springframework.mail.javamail.MimeMessageHelper helper =
                    new org.springframework.mail.javamail.MimeMessageHelper(mimeMessage, true, "UTF-8");

            // Đọc địa chỉ gửi thư từ ENV hoặc properties
            String fromEmail = System.getenv("MAIL_FROM");
            if (fromEmail == null || fromEmail.isEmpty()) {
                fromEmail = env.getProperty("mail.from", env.getProperty("mail.username", "unrealworld2002@gmail.com"));
            }
            if (fromEmail != null) fromEmail = fromEmail.trim();
            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject("[Phone Store] Đặt lại mật khẩu");

            // Đọc Base URL từ ENV (khi deploy online, ví dụ: https://my-app.onrender.com) hoặc local
            String baseUrl = System.getenv("APP_BASE_URL");
            if (baseUrl == null || baseUrl.isEmpty()) {
                baseUrl = env.getProperty("app.baseUrl", "http://localhost:8080");
            }
            if (baseUrl != null) baseUrl = baseUrl.trim();
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }
            String resetUrl = baseUrl + "/auth/reset-password?token=" + token;
            String htmlContent = "<div style=\"font-family: Arial, sans-serif; line-height: 1.6; color: #333;\">"
                    + "<h2 style=\"color: #007bff;\">Phone Store - Yêu cầu đặt lại mật khẩu</h2>"
                    + "<p>Xin chào <b>" + user.getFullName() + "</b>,</p>"
                    + "<p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.</p>"
                    + "<p>Vui lòng nhấn vào nút bên dưới để đặt lại mật khẩu (liên kết có hiệu lực trong <b>15 phút</b>):</p>"
                    + "<p style=\"margin: 20px 0;\">"
                    + "   <a href=\"" + resetUrl + "\" style=\"display: inline-block; padding: 12px 24px; font-size: 15px; color: #ffffff; background-color: #007bff; text-decoration: none; border-radius: 6px; font-weight: bold;\">Đặt lại mật khẩu</a>"
                    + "</p>"
                    + "<p>Hoặc bạn có thể sao chép liên kết sau vào trình duyệt:</p>"
                    + "<p><a href=\"" + resetUrl + "\" style=\"color: #007bff;\">" + resetUrl + "</a></p>"
                    + "<hr style=\"border: none; border-top: 1px solid #eee; margin: 20px 0;\">"
                    + "<p style=\"font-size: 13px; color: #777;\">Nếu bạn không gửi yêu cầu này, vui lòng bỏ qua email.</p>"
                    + "<p>Trân trọng,<br><b>Phone Store Team</b></p>"
                    + "</div>";

            helper.setText(htmlContent, true); // true = gửi định dạng HTML
            System.out.println("⏳ Đang gửi email đặt lại mật khẩu đến: " + email + " qua " + fromEmail);
            mailSender.send(mimeMessage);
            System.out.println("✅ ĐÃ GỬI EMAIL THÀNH CÔNG ĐẾN: " + email);
        } catch (Exception e) {
            System.err.println("❌ LỖI GỬI EMAIL ĐẾN " + email + ": " + e.getMessage());
            e.printStackTrace();
        }

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
