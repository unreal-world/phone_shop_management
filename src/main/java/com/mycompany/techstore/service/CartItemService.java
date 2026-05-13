package com.mycompany.techstore.service;

import java.util.List;

import com.mycompany.techstore.model.CartItem;

public interface CartItemService {
    List<CartItem> getAllCartItems();
    CartItem getCartItemById(String cartItemID);
    List<CartItem> getCartItemsByCartId(String cartID);
    void saveCartItem(CartItem cartItem);
    void updateCartItem(CartItem cartItem);
    void deleteCartItem(String cartItemID);
}
