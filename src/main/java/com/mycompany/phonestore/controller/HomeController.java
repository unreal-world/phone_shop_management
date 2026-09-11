package com.mycompany.phonestore.controller;

import com.mycompany.phonestore.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(HttpSession session, Model model,
                        @RequestParam(value = "keyword", required = false) String keyword,
                        @RequestParam(value = "sort", required = false) String sort) {
        com.mycompany.phonestore.model.User user =
                (com.mycompany.phonestore.model.User) session.getAttribute("loggedInUser");

        // Lấy danh sách sản phẩm với cả tìm kiếm và sắp xếp
        java.util.List<com.mycompany.phonestore.model.Product> products =
                productService.getProducts(keyword, sort);

        // Ẩn sản phẩm đã xóa với CUSTOMER
        boolean isAdmin = user != null && "ADMIN".equals(user.getRole().name());
        if (!isAdmin) {
            products = new java.util.ArrayList<>(products);
            products.removeIf(p -> p.getIsDeleted());
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort); // Để dropdown giữ nguyên trạng thái đã chọn
        return "home";
    }
}
