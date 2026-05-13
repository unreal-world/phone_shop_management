package com.mycompany.techstore.dao;

import com.mycompany.techstore.model.Product;
import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
}