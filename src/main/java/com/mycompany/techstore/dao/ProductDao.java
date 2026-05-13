package com.mycompany.techstore.dao;

import java.util.List;

import com.mycompany.techstore.model.Product;

public interface ProductDao {
    List<Product> getAllProducts();
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(String productID);
    Product getProductById(String productID);
}