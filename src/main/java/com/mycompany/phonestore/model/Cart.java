package com.mycompany.phonestore.model;

import java.util.List;

public class Cart {
    private String cartID;
    private List<CartItem> cartItem;

    public Cart() {}

    public Cart(String cartID) {
        this.cartID = cartID;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public List<CartItem> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItem> cartItem) {
        this.cartItem = cartItem;
    }
}
