package com.mycompany.phonestore.service;

import com.mycompany.phonestore.model.Cart;

public interface CartService {
    Cart getCartById(String cartID);
    Cart getCartByUserId(String userID);
    void saveCart(Cart cart);
    void updateCart(Cart cart);
    void deleteCart(String cartID);
}
