package com.mycompany.techstore.controller;

import com.mycompany.techstore.model.Order;
import com.mycompany.techstore.model.OrderDetail;
import com.mycompany.techstore.model.OrderStatus;
import com.mycompany.techstore.model.Product;
import com.mycompany.techstore.model.User;
import com.mycompany.techstore.service.OrderDetailService;
import com.mycompany.techstore.service.OrderService;
import com.mycompany.techstore.service.ProductService;
import com.mycompany.techstore.service.AddressService;
import com.mycompany.techstore.model.Address;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private AddressService addressService;

    // View cart
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        Map<Product, Integer> cartItems = new HashMap<>();
        double total = 0.0;

        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            Product product = productService.getProductById(entry.getKey());
            if (product != null) {
                cartItems.put(product, entry.getValue());
                total += product.getPrice() * entry.getValue();
            }
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            model.addAttribute("userAddresses", addressService.getAddressesByUserId(user.getUserID()));
        }

        return "cart";
    }

    // Add to cart
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") String productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng đăng nhập để thực hiện thêm sản phẩm vào giỏ hàng.");
            return "redirect:/auth/login";
        }
        
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        if (cart.containsKey(productId)) {
            cart.put(productId, cart.get(productId) + quantity);
        } else {
            cart.put(productId, quantity);
        }

        session.setAttribute("cart", cart);
        redirectAttributes.addFlashAttribute("successMessage", "Đã thêm sản phẩm vào giỏ hàng thành công!");
        return "redirect:/";
    }

    // Update cart
    @PostMapping("/update")
    public String updateCart(@RequestParam("productId") String productId,
                             @RequestParam("quantity") int quantity,
                             HttpSession session) {
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
        if (cart != null) {
            if (quantity <= 0) {
                cart.remove(productId);
            } else {
                cart.put(productId, quantity);
            }
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    // Remove from cart
    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable("productId") String productId, HttpSession session) {
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
        if (cart != null) {
            cart.remove(productId);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    // Checkout
    @PostMapping("/checkout")
    public String checkout(@RequestParam("receiver") String receiver,
                           @RequestParam("phoneNumber") String phoneNumber,
                           @RequestParam("selectedAddressId") String selectedAddressId,
                           @RequestParam(value = "city", required = false) String city,
                           @RequestParam(value = "ward", required = false) String ward,
                           @RequestParam(value = "street", required = false) String street,
                           @RequestParam(value = "houseNumber", required = false) String houseNumber,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng đăng nhập để thanh toán.");
            return "redirect:/auth/login";
        }

        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Giỏ hàng trống.");
            return "redirect:/cart";
        }

        Address finalAddress = null;
        
        if ("new".equals(selectedAddressId)) {
            Address newAddress = new Address();
            newAddress.setAddressID(UUID.randomUUID().toString());
            newAddress.setUserID(user.getUserID());
            newAddress.setCity(city);
            newAddress.setWard(ward);
            newAddress.setStreet(street);
            newAddress.setHouseNumber(houseNumber);
            addressService.saveAddress(newAddress);
            finalAddress = newAddress;
        } else {
            finalAddress = addressService.getAddressById(selectedAddressId);
        }

        // Create Order
        Order order = new Order();
        order.setOrderID(UUID.randomUUID().toString());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setAddress(finalAddress);
        order.setReceiver(receiver);
        order.setPhoneNumber(phoneNumber);
        order.setUserID(user.getUserID());
        
        orderService.saveOrder(order);

        // Create OrderDetails
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            Product product = productService.getProductById(entry.getKey());
            if (product != null) {
                OrderDetail detail = new OrderDetail();
                detail.setOrderDetailID(UUID.randomUUID().toString());
                detail.setOrderID(order.getOrderID());
                detail.setProductID(product.getProductID());
                detail.setQuantity(entry.getValue());
                detail.setUnitPrice(product.getPrice());
                detail.setTotalPrice(product.getPrice() * entry.getValue());
                
                orderDetailService.saveOrderDetail(detail);
                
                // Optional: Reduce stock_quantity
                product.setStock_quantity(product.getStock_quantity() - entry.getValue());
                productService.updateProduct(product);
            }
        }

        // Clear cart
        session.removeAttribute("cart");
        
        redirectAttributes.addFlashAttribute("successMessage", "Đặt hàng thành công! Mã đơn hàng: " + order.getOrderID());
        return "redirect:/";
    }
}
