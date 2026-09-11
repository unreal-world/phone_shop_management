package com.mycompany.phonestore.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.phonestore.dao.ProductDao;
import com.mycompany.phonestore.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT p.*, (SELECT imageSource FROM Image i WHERE i.productID = p.productID LIMIT 1) as primaryImage FROM Product p";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product p = new Product(
                rs.getString("productID"),
                rs.getString("productName"),
                rs.getString("brand"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getInt("stock_quantity")
            );
            p.setPrimaryImage(rs.getString("primaryImage"));
            p.setIsDeleted(rs.getBoolean("isDeleted"));
            return p;
        });
    }

    @Override
    public void addProduct(Product p) {
        String sql = "INSERT INTO Product (productID, productName, brand, price, description, stock_quantity) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, p.getProductID(), p.getProductName(), p.getBrand(), p.getPrice(), p.getDescription(), p.getStock_quantity());
    }

    @Override
    public void updateProduct(Product p) {
        String sql = "UPDATE Product SET productName=?, brand=?, price=?, description=?, stock_quantity=? WHERE productID=?";
        jdbcTemplate.update(sql, p.getProductName(), p.getBrand(), p.getPrice(), p.getDescription(), p.getStock_quantity(), p.getProductID());
    }

    @Override
    public void deleteProduct(String productID) {
        String sql = "UPDATE Product SET isDeleted = 1 WHERE productID = ?";
        jdbcTemplate.update(sql, productID);
    }

    @Override
    public Product getProductById(String productID) {
        String sql = "SELECT p.*, (SELECT imageSource FROM Image i WHERE i.productID = p.productID LIMIT 1) as primaryImage FROM Product p WHERE p.productID=?";
        List<Product> products = jdbcTemplate.query(sql, new Object[]{productID}, (rs, rowNum) -> {
            Product p = new Product(
                rs.getString("productID"),
                rs.getString("productName"),
                rs.getString("brand"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getInt("stock_quantity")
            );
            p.setPrimaryImage(rs.getString("primaryImage"));
            p.setIsDeleted(rs.getBoolean("isDeleted"));
            return p;
        });
        return products.isEmpty() ? null : products.get(0);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        String sql = "SELECT p.*, (SELECT imageSource FROM Image i WHERE i.productID = p.productID LIMIT 1) as primaryImage FROM Product p WHERE LOWER(p.productName) LIKE LOWER(?)";
        String searchPattern = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new Object[]{searchPattern}, (rs, rowNum) -> {
            Product p = new Product(
                rs.getString("productID"),
                rs.getString("productName"),
                rs.getString("brand"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getInt("stock_quantity")
            );
            p.setPrimaryImage(rs.getString("primaryImage"));
            p.setIsDeleted(rs.getBoolean("isDeleted"));
            return p;
        });
    }

    @Override
    public List<Product> getProducts(String keyword, String sort) {
        // Whitelist để tránh SQL Injection - KHÔNG ghép sort trực tiếp vào SQL
        String orderBy;
        if ("price_asc".equals(sort)) {
            orderBy = " ORDER BY p.price ASC";
        } else if ("price_desc".equals(sort)) {
            orderBy = " ORDER BY p.price DESC";
        } else {
            orderBy = " ORDER BY p.productID DESC"; // Mặc định: sản phẩm mới nhất trước
        }

        String baseSelect = "SELECT p.*, (SELECT imageSource FROM Image i WHERE i.productID = p.productID LIMIT 1) as primaryImage FROM Product p";

        String sql;
        Object[] params;
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql = baseSelect + " WHERE LOWER(p.productName) LIKE LOWER(?)" + orderBy;
            params = new Object[]{"%" + keyword.trim() + "%"};
        } else {
            sql = baseSelect + orderBy;
            params = new Object[]{};
        }

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Product p = new Product(
                rs.getString("productID"),
                rs.getString("productName"),
                rs.getString("brand"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getInt("stock_quantity")
            );
            p.setPrimaryImage(rs.getString("primaryImage"));
            p.setIsDeleted(rs.getBoolean("isDeleted"));
            return p;
        });
    }
}
