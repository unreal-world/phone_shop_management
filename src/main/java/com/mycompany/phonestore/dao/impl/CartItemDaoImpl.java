package com.mycompany.phonestore.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.phonestore.dao.CartItemDao;
import com.mycompany.phonestore.model.CartItem;

@Repository
public class CartItemDaoImpl implements CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public CartItemDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CartItem> getAllCartItems() {
        String sql = "SELECT * FROM CartItem";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CartItem(
                rs.getString("cartItemID"),
                rs.getString("cartID"),
                rs.getString("productID"),
                rs.getInt("quantity")
        ));
    }

    @Override
    public CartItem getCartItemById(String cartItemID) {
        String sql = "SELECT * FROM CartItem WHERE cartItemID = ?";
        List<CartItem> cartItems = jdbcTemplate.query(sql, new Object[]{cartItemID}, (rs, rowNum) -> new CartItem(
                rs.getString("cartItemID"),
                rs.getString("cartID"),
                rs.getString("productID"),
                rs.getInt("quantity")
        ));
        return cartItems.isEmpty() ? null : cartItems.get(0);
    }

    @Override
    public List<CartItem> getCartItemsByCartId(String cartID) {
        String sql = "SELECT * FROM CartItem WHERE cartID = ?";
        return jdbcTemplate.query(sql, new Object[]{cartID}, (rs, rowNum) -> new CartItem(
                rs.getString("cartItemID"),
                rs.getString("cartID"),
                rs.getString("productID"),
                rs.getInt("quantity")
        ));
    }

    @Override
    public void addCartItem(CartItem cartItem) {
        String sql = "INSERT INTO CartItem (cartItemID, cartID, productID, quantity) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, cartItem.getCartItemID(), cartItem.getCartID(), cartItem.getProductID(), cartItem.getQuantity());
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        String sql = "UPDATE CartItem SET cartID=?, productID=?, quantity=? WHERE cartItemID=?";
        jdbcTemplate.update(sql, cartItem.getCartID(), cartItem.getProductID(), cartItem.getQuantity(), cartItem.getCartItemID());
    }

    @Override
    public void deleteCartItem(String cartItemID) {
        String sql = "DELETE FROM CartItem WHERE cartItemID=?";
        jdbcTemplate.update(sql, cartItemID);
    }
}
