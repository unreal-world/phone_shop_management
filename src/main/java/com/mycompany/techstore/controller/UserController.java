package com.mycompany.techstore.controller;

import com.mycompany.techstore.model.User;
import com.mycompany.techstore.service.UserService;
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
}
