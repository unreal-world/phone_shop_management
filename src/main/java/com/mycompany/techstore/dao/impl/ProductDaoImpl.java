package com.mycompany.techstore.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.techstore.dao.ProductDao;
import com.mycompany.techstore.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM Product";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Product(
                rs.getString("productID"),
                rs.getString("productName"),
                rs.getString("brand"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getInt("stock_quantity")
        ));
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
        String sql = "DELETE FROM Product WHERE productID=?";
        jdbcTemplate.update(sql, productID);
    }

    @Override
    public Product getProductById(String productID) {
        String sql = "SELECT * FROM Product WHERE productID=?";
        List<Product> products = jdbcTemplate.query(sql, new Object[]{productID}, (rs, rowNum) -> new Product(
                rs.getString("productID"),
                rs.getString("productName"),
                rs.getString("brand"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getInt("stock_quantity")
        ));
        return products.isEmpty() ? null : products.get(0);
    }
}
