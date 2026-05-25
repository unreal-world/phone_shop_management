package com.mycompany.techstore.controller;

import com.mycompany.techstore.model.Product; // Import lớp Model của bạn
import com.mycompany.techstore.model.Image;
import com.mycompany.techstore.service.ProductService; // Import lớp Service của bạn
import com.mycompany.techstore.dao.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageDao imageDao;

    // Đã xóa hàm listProducts vì dùng chung trang chủ

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam(value = "productImage", required = false) MultipartFile productImage,
                              HttpServletRequest request) {
        boolean isNew = false;
        if (product.getProductID() == null || product.getProductID().trim().isEmpty()) {
            product.setProductID(java.util.UUID.randomUUID().toString());
            productService.saveProduct(product);
            isNew = true;
        } else {
            productService.updateProduct(product);
        }

        if (productImage != null && !productImage.isEmpty()) {
            try {
                String base64Image = Base64.getEncoder().encodeToString(productImage.getBytes());
                String imageString = "data:" + productImage.getContentType() + ";base64," + base64Image;

                Image img = new Image();
                img.setImageID(java.util.UUID.randomUUID().toString());
                img.setProductID(product.getProductID());
                img.setImageSource(imageString); 
                imageDao.addImage(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/";
    }

    @GetMapping("/edit/{productID}")
    public String showEditForm(@PathVariable("productID") String productID, Model model) {
        model.addAttribute("product", productService.getProductById(productID));
        return "products/form";
    }

    @GetMapping("/delete/{productID}")
    public String deleteProduct(@PathVariable("productID") String productID) {
        productService.deleteProduct(productID);
        return "redirect:/";
    }
}