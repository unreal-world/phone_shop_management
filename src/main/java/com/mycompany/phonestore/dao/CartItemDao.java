package com.mycompany.phonestore.dao;

import java.util.List;

import com.mycompany.phonestore.model.CartItem;

public interface CartItemDao {
    List<CartItem> getAllCartItems();
    CartItem getCartItemById(String cartItemID);
    List<CartItem> getCartItemsByCartId(String cartID);
    void addCartItem(CartItem cartItem);
    void updateCartItem(CartItem cartItem);
    void deleteCartItem(String cartItemID);
}
