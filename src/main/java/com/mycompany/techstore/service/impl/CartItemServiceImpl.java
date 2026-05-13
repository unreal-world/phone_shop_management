package com.mycompany.techstore.service.impl;

import com.mycompany.techstore.dao.CartItemDao;
import com.mycompany.techstore.model.CartItem;
import com.mycompany.techstore.service.CartItemService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemDao cartItemDao;

    public CartItemServiceImpl(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemDao.getAllCartItems();
    }

    @Override
    public CartItem getCartItemById(String cartItemID) {
        return cartItemDao.getCartItemById(cartItemID);
    }

    @Override
    public List<CartItem> getCartItemsByCartId(String cartID) {
        return cartItemDao.getCartItemsByCartId(cartID);
    }

    @Override
    public void saveCartItem(CartItem cartItem) {
        cartItemDao.addCartItem(cartItem);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        cartItemDao.updateCartItem(cartItem);
    }

    @Override
    public void deleteCartItem(String cartItemID) {
        cartItemDao.deleteCartItem(cartItemID);
    }
}
