package com.mycompany.techstore.service.impl;

import com.mycompany.techstore.dao.CartDao;
import com.mycompany.techstore.model.Cart;
import com.mycompany.techstore.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;

    public CartServiceImpl(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public Cart getCartById(String cartID) {
        return cartDao.getCartById(cartID);
    }

    @Override
    public Cart getCartByUserId(String userID) {
        return cartDao.getCartByUserId(userID);
    }

    @Override
    public void saveCart(Cart cart) {
        cartDao.addCart(cart);
    }

    @Override
    public void updateCart(Cart cart) {
        cartDao.updateCart(cart);
    }

    @Override
    public void deleteCart(String cartID) {
        cartDao.deleteCart(cartID);
    }
}
