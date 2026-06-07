package com.mycompany.phonestore.service.impl;

import com.mycompany.phonestore.dao.ProductDao;
import com.mycompany.phonestore.model.Product;
import com.mycompany.phonestore.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public void saveProduct(Product product) {
        // Có thể thêm logic kiểm tra ở đây trước khi gọi DAO
        productDao.addProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    @Override
    public void deleteProduct(String productID) {
        productDao.deleteProduct(productID);
    }

    @Override
    public Product getProductById(String productID) {
        return productDao.getProductById(productID);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return productDao.searchProducts(keyword);
    }
}
