package com.mycompany.techstore.service;

import java.util.List;

import com.mycompany.techstore.model.Product;

public interface ProductService {
    List<Product> getAllProducts();
    void saveProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(String productID);
    Product getProductById(String productID);
}