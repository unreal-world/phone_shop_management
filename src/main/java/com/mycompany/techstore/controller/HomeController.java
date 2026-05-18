package com.mycompany.techstore.controller;

import com.mycompany.techstore.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") != null) {
            model.addAttribute("products", productService.getAllProducts());
        }
        return "home";
    }
}