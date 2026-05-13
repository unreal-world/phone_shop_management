package com.mycompany.techstore.controller;

import com.mycompany.techstore.model.Cart;
import com.mycompany.techstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{cartID}")
    public String viewCart(@PathVariable("cartID") String cartID, Model model) {
        model.addAttribute("cart", cartService.getCartById(cartID));
        return "cart/view";
    }

    @GetMapping("/user/{userID}")
    public String getCartByUser(@PathVariable("userID") String userID, Model model) {
        model.addAttribute("cart", cartService.getCartByUserId(userID));
        return "cart/view";
    }

    @PostMapping("/save")
    public String saveCart(@ModelAttribute("cart") Cart cart) {
        if (cart.getCartID() == null) {
            cartService.saveCart(cart);
        } else {
            cartService.updateCart(cart);
        }
        return "redirect:/cart/" + cart.getCartID();
    }

    @GetMapping("/delete/{cartID}")
    public String deleteCart(@PathVariable("cartID") String cartID) {
        cartService.deleteCart(cartID);
        return "redirect:/";
    }
}
