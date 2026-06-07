package com.mycompany.phonestore.controller;

import com.mycompany.phonestore.model.User;
import com.mycompany.phonestore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        if (user.getUserID() == null) {
            userService.saveUser(user);
        } else {
            userService.updateUser(user);
        }
        return "redirect:/users/list";
    }

    @GetMapping("/edit/{userID}")
    public String showEditForm(@PathVariable("userID") String userID, Model model) {
        model.addAttribute("user", userService.getUserById(userID));
        return "users/form";
    }

    @GetMapping("/delete/{userID}")
    public String deleteUser(@PathVariable("userID") String userID) {
        userService.deleteUser(userID);
        return "redirect:/users/list";
    }

    @GetMapping("/view/{userID}")
    public String viewUser(@PathVariable("userID") String userID, Model model) {
        model.addAttribute("user", userService.getUserById(userID));
        return "users/detail";
    }

    // ==================== QUẢN LÝ TÀI KHOẢN ====================

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/auth/login";
        }
        // Lấy thông tin mới nhất từ DB
        User user = userService.getUserById(loggedInUser.getUserID());
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("fullName") String fullName,
                                @RequestParam("email") String email,
                                @RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam(value = "newPassword", required = false) String newPassword,
                                @RequestParam(value = "confirmNewPassword", required = false) String confirmNewPassword,
                                HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/auth/login";
        }

        // Lấy user mới nhất từ DB
        User user = userService.getUserById(loggedInUser.getUserID());

        // Kiểm tra email trùng (nếu thay đổi)
        if (email != null && !email.isEmpty() && !email.equals(user.getEmail())) {
            if (userService.existsByEmail(email)) {
                model.addAttribute("error", "Email đã được sử dụng bởi tài khoản khác!");
                model.addAttribute("user", user);
                return "user/profile";
            }
        }

        // Cập nhật thông tin cơ bản
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        // Cập nhật mật khẩu nếu có nhập mới
        if (newPassword != null && !newPassword.isEmpty()) {
            if (!newPassword.equals(confirmNewPassword)) {
                model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
                model.addAttribute("user", user);
                return "user/profile";
            }
            user.setPassword(newPassword);
        }

        userService.updateUser(user);

        // Cập nhật lại session
        session.setAttribute("loggedInUser", user);

        model.addAttribute("success", "Cập nhật thông tin thành công!");
        model.addAttribute("user", user);
        return "user/profile";
    }
}
