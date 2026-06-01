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
    public String index(HttpSession session, Model model, @org.springframework.web.bind.annotation.RequestParam(value = "keyword", required = false) String keyword) {
        com.mycompany.techstore.model.User user = (com.mycompany.techstore.model.User) session.getAttribute("loggedInUser");
        if (user != null) {
            java.util.List<com.mycompany.techstore.model.Product> products;
            if (keyword != null && !keyword.trim().isEmpty()) {
                products = productService.searchProducts(keyword.trim());
                model.addAttribute("keyword", keyword.trim());
            } else {
                products = productService.getAllProducts();
            }
            
            if (!"ADMIN".equals(user.getRole().name())) {
                products = new java.util.ArrayList<>(products);
                products.removeIf(p -> p.getIsDeleted());
            }
            model.addAttribute("products", products);
        }
        return "home";
    }
}