package com.mycompany.techstore.dao.impl;

import com.mycompany.techstore.dao.ProductDao;
import com.mycompany.techstore.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("brand"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getInt("stock_quantity")
        ));
    }

    @Override
    public void addProduct(Product p) {
        String sql = "INSERT INTO products (name, brand, price, description, stock_quantity) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, p.getName(), p.getBrand(), p.getPrice(), p.getDescription(), p.getStockQuantity());
    }

    @Override
    public void updateProduct(Product p) {
        String sql = "UPDATE products SET name=?, brand=?, price=?, description=?, stock_quantity=? WHERE id=?";
        jdbcTemplate.update(sql, p.getName(), p.getBrand(), p.getPrice(), p.getDescription(), p.getStockQuantity(), p.getId());
    }

    @Override
    public void deleteProduct(Long id) {
        String sql = "DELETE FROM products WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Product getProductById(Long id) {
        String sql = "SELECT * FROM products WHERE id=?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("brand"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getInt("stock_quantity")
        ), id);
    }
}
