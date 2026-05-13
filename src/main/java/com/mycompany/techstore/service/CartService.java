package com.mycompany.techstore.service;

import com.mycompany.techstore.model.Cart;

public interface CartService {
    Cart getCartById(String cartID);
    Cart getCartByUserId(String userID);
    void saveCart(Cart cart);
    void updateCart(Cart cart);
    void deleteCart(String cartID);
}
