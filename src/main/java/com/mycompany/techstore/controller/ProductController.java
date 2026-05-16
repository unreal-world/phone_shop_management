package com.mycompany.techstore.controller;

import com.mycompany.techstore.model.Product; // Import lớp Model của bạn
import com.mycompany.techstore.service.ProductService; // Import lớp Service của bạn
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        if (product.getProductID() == null || product.getProductID().trim().isEmpty()) {
            product.setProductID(java.util.UUID.randomUUID().toString());
            productService.saveProduct(product);
        } else {
            productService.updateProduct(product);
        }
        return "redirect:/products/list";
    }

    @GetMapping("/edit/{productID}")
    public String showEditForm(@PathVariable("productID") String productID, Model model) {
        model.addAttribute("product", productService.getProductById(productID));
        return "products/form";
    }

    @GetMapping("/delete/{productID}")
    public String deleteProduct(@PathVariable("productID") String productID) {
        productService.deleteProduct(productID);
        return "redirect:/products/list";
    }
}