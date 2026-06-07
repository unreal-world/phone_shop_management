package com.mycompany.phonestore.dao;

import java.util.List;

import com.mycompany.phonestore.model.Product;

public interface ProductDao {
    List<Product> getAllProducts();
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(String productID);
    Product getProductById(String productID);
    List<Product> searchProducts(String keyword);
}
