package com.mycompany.techstore.dao;

import com.mycompany.techstore.model.Cart;

public interface CartDao {
    Cart getCartById(String cartID);
    Cart getCartByUserId(String userID);
    void addCart(Cart cart);
    void updateCart(Cart cart);
    void deleteCart(String cartID);
}
