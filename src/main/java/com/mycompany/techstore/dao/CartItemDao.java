package com.mycompany.techstore.dao;

import java.util.List;

import com.mycompany.techstore.model.CartItem;

public interface CartItemDao {
    List<CartItem> getAllCartItems();
    CartItem getCartItemById(String cartItemID);
    List<CartItem> getCartItemsByCartId(String cartID);
    void addCartItem(CartItem cartItem);
    void updateCartItem(CartItem cartItem);
    void deleteCartItem(String cartItemID);
}
