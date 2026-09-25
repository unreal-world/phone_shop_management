package com.mycompany.phonestore.controller;

import com.mycompany.phonestore.model.User;
import com.mycompany.phonestore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/auth", "/phonestore/auth"})
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
        // Validate tên đăng nhập
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            model.addAttribute("error", "Tên đăng nhập không được để trống!");
            model.addAttribute("user", user);
            return "auth/register";
        }
        user.setUsername(user.getUsername().trim());

        // Validate mật khẩu
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            model.addAttribute("error", "Mật khẩu không được để trống!");
            model.addAttribute("user", user);
            return "auth/register";
        }

        // Validate mật khẩu xác nhận
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            model.addAttribute("user", user);
            return "auth/register";
        }

        // Validate username đã tồn tại
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
            model.addAttribute("user", user);
            return "auth/register";
        }

        // Validate họ và tên là bắt buộc
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            model.addAttribute("error", "Họ và tên không được để trống!");
            model.addAttribute("user", user);
            return "auth/register";
        }
        user.setFullName(user.getFullName().trim());

        // Validate email là bắt buộc
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            model.addAttribute("error", "Email là bắt buộc!");
            model.addAttribute("user", user);
            return "auth/register";
        }
        user.setEmail(user.getEmail().trim());

        // Validate định dạng email chuẩn
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!user.getEmail().matches(emailPattern)) {
            model.addAttribute("error", "Email không đúng định dạng chuẩn (ví dụ: example@email.com)!");
            model.addAttribute("user", user);
            return "auth/register";
        }

        // Validate email đã tồn tại
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email đã được sử dụng!");
            model.addAttribute("user", user);
            return "auth/register";
        }

        if (user.getFullName() != null) {
            user.setFullName(user.getFullName().trim());
        }
        if (user.getPhoneNumber() != null) {
            user.setPhoneNumber(user.getPhoneNumber().trim());
        }

        userService.register(user);
        model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "auth/login";
    }

    // ==================== QUÊN MẬT KHẨU ====================

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(@RequestParam(value = "username", required = false) String username, Model model) {
        if (username != null && !username.trim().isEmpty()) {
            username = username.trim();
            User user = userService.getUserByUsername(username);
            if (user == null) {
                model.addAttribute("error", "Tài khoản \"" + username + "\" không tồn tại trong hệ thống!");
                model.addAttribute("username", username);
            } else if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                model.addAttribute("error", "Tài khoản \"" + username + "\" chưa thiết lập địa chỉ email!");
                model.addAttribute("username", username);
            } else {
                model.addAttribute("user", user);
                model.addAttribute("username", user.getUsername());
                model.addAttribute("email", user.getEmail());
            }
        } else {
            model.addAttribute("error", "Vui lòng nhập tên tài khoản trước khi chọn Quên mật khẩu!");
        }
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam(value = "username", required = false) String username,
                                        @RequestParam(value = "email", required = false) String email,
                                        Model model) {
        User user = null;
        if (username != null && !username.trim().isEmpty()) {
            user = userService.getUserByUsername(username.trim());
        } else if (email != null && !email.trim().isEmpty()) {
            user = userService.getUserByEmail(email.trim());
        }

        if (user == null) {
            model.addAttribute("error", "Không tìm thấy tài khoản tương ứng!");
            if (username != null) model.addAttribute("username", username);
            return "auth/forgot-password";
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            model.addAttribute("error", "Tài khoản này chưa có email đăng ký!");
            model.addAttribute("username", user.getUsername());
            return "auth/forgot-password";
        }

        String targetEmail = user.getEmail().trim();
        String token = userService.createPasswordResetToken(targetEmail);
        if (token == null) {
            model.addAttribute("error", "Không thể gửi email đặt lại mật khẩu. Vui lòng thử lại!");
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", targetEmail);
            return "auth/forgot-password";
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", targetEmail);
        model.addAttribute("success", "Đã gửi email đặt lại mật khẩu đến: " + targetEmail + ". Vui lòng kiểm tra hộp thư của bạn!");
        return "auth/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam(value = "token", required = false) String token, Model model) {
        if (token == null || token.trim().isEmpty()) {
            model.addAttribute("error", "Liên kết không hợp lệ (thiếu mã token xác thực)!");
            return "auth/reset-password";
        }
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
