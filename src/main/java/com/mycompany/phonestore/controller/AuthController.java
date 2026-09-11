package com.mycompany.phonestore.controller;

import com.mycompany.phonestore.model.User;
import com.mycompany.phonestore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // ==================== ĐĂNG NHẬP ====================

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpSession session, Model model) {
        User user = userService.authenticate(username, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            model.addAttribute("username", username);
            return "auth/login";
        }
    }

    // ==================== ĐĂNG KÝ ====================

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user,
                                  @RequestParam("confirmPassword") String confirmPassword,
                                  Model model) {
        // Validate mật khẩu xác nhận
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "auth/register";
        }

        // Validate username đã tồn tại
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "auth/register";
        }

        // Validate email đã tồn tại
        if (user.getEmail() != null && !user.getEmail().isEmpty()
                && userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email đã được sử dụng!");
            return "auth/register";
        }

        userService.register(user);
        model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "auth/login";
    }

    // ==================== QUÊN MẬT KHẨU ====================

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        String token = userService.createPasswordResetToken(email);
        if (token == null) {
            model.addAttribute("error", "Email không tồn tại trong hệ thống!");
            return "auth/forgot-password";
        }
        model.addAttribute("success", "Đã gửi email đặt lại mật khẩu. Vui lòng kiểm tra hòm thư của bạn!");
        return "auth/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        String userID = userService.validatePasswordResetToken(token);
        if (userID == null) {
            model.addAttribute("error", "Liên kết đặt lại mật khẩu không hợp lệ hoặc đã hết hạn!");
            return "auth/reset-password";
        }
        model.addAttribute("token", token);
        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            model.addAttribute("token", token);
            return "auth/reset-password";
        }

        String userID = userService.validatePasswordResetToken(token);
        if (userID == null) {
            model.addAttribute("error", "Liên kết đặt lại mật khẩu không hợp lệ hoặc đã hết hạn!");
            return "auth/reset-password";
        }

        userService.resetPassword(token, newPassword);
        model.addAttribute("success", "Đặt lại mật khẩu thành công! Vui lòng đăng nhập.");
        return "auth/login";
    }

    // ==================== ĐĂNG XUẤT ====================

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
