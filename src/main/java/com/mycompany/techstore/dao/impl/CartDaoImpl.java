package com.mycompany.techstore.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.techstore.dao.CartDao;
import com.mycompany.techstore.model.Cart;

@Repository
public class CartDaoImpl implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cart getCartById(String cartID) {
        String sql = "SELECT * FROM Cart WHERE cartID = ?";
        List<Cart> carts = jdbcTemplate.query(sql, new Object[]{cartID}, (rs, rowNum) -> new Cart(rs.getString("cartID")));
        return carts.isEmpty() ? null : carts.get(0);
    }

    @Override
    public Cart getCartByUserId(String userID) {
        String sql = "SELECT * FROM Cart WHERE userID = ?";
        List<Cart> carts = jdbcTemplate.query(sql, new Object[]{userID}, (rs, rowNum) -> new Cart(rs.getString("cartID")));
        return carts.isEmpty() ? null : carts.get(0);
    }

    @Override
    public void addCart(Cart cart) {
        String sql = "INSERT INTO Cart (cartID, userID) VALUES (?, ?)";
        // Note: We need userID for this query. Consider adding it to Cart model or adjusting this.
        jdbcTemplate.update(sql, cart.getCartID());
    }

    @Override
    public void updateCart(Cart cart) {
        String sql = "UPDATE Cart SET userID=? WHERE cartID=?";
        jdbcTemplate.update(sql, cart.getCartID());
    }

    @Override
    public void deleteCart(String cartID) {
        String sql = "DELETE FROM Cart WHERE cartID=?";
        jdbcTemplate.update(sql, cartID);
    }
}
