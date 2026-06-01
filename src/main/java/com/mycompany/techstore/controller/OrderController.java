package com.mycompany.techstore.controller;

import com.mycompany.techstore.model.Order;
import com.mycompany.techstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private com.mycompany.techstore.service.UserService userService;

    @GetMapping("/list")
    public String listOrders(Model model) {
        java.util.List<Order> allOrders = orderService.getAllOrders();
        java.util.Map<com.mycompany.techstore.model.User, java.util.List<Order>> groupedOrders = new java.util.HashMap<>();
        java.util.Map<String, com.mycompany.techstore.model.User> userCache = new java.util.HashMap<>();
        
        for (Order order : allOrders) {
            String uid = order.getUserID();
            com.mycompany.techstore.model.User user = userCache.computeIfAbsent(uid, k -> userService.getUserById(k));
            
            if (user != null) {
                groupedOrders.computeIfAbsent(user, k -> new java.util.ArrayList<>()).add(order);
            }
        }
        
        model.addAttribute("groupedOrders", groupedOrders);
        return "orders/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("order", new Order());
        return "orders/form";
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute("order") Order order) {
        if (order.getOrderID() == null) {
            orderService.saveOrder(order);
        } else {
            orderService.updateOrder(order);
        }
        return "redirect:/orders/list";
    }

    @GetMapping("/edit/{orderID}")
    public String showEditForm(@PathVariable("orderID") String orderID, Model model) {
        model.addAttribute("order", orderService.getOrderById(orderID));
        return "orders/form";
    }

    @GetMapping("/delete/{orderID}")
    public String deleteOrder(@PathVariable("orderID") String orderID) {
        orderService.deleteOrder(orderID);
        return "redirect:/orders/list";
    }

    @GetMapping("/user/{userID}")
    public String getOrdersByUser(@PathVariable("userID") String userID, Model model) {
        java.util.List<Order> orders = orderService.getOrdersByUserId(userID);
        model.addAttribute("orders", orders);
        
        java.util.Map<String, java.util.List<String>> orderImages = new java.util.HashMap<>();
        java.util.Map<String, String> orderProductNames = new java.util.HashMap<>();
        java.util.Map<String, Double> orderTotals = new java.util.HashMap<>();
        for (Order order : orders) {
            orderImages.put(order.getOrderID(), orderService.getProductImagesForOrder(order.getOrderID()));
            java.util.List<String> names = orderService.getProductNamesForOrder(order.getOrderID());
            orderProductNames.put(order.getOrderID(), String.join(", ", names));
            orderTotals.put(order.getOrderID(), orderService.getOrderTotalValue(order.getOrderID()));
        }
        model.addAttribute("orderImages", orderImages);
        model.addAttribute("orderProductNames", orderProductNames);
        model.addAttribute("orderTotals", orderTotals);
        return "orders/user-orders";
    }

    @GetMapping("/view/{orderID}")
    public String viewOrder(@PathVariable("orderID") String orderID, Model model) {
        model.addAttribute("order", orderService.getOrderById(orderID));
        return "orders/detail";
    }

    @GetMapping("/my-orders")
    public String myOrders(jakarta.servlet.http.HttpSession session, Model model) {
        com.mycompany.techstore.model.User user = (com.mycompany.techstore.model.User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/auth/login";
        }
        java.util.List<Order> orders = orderService.getOrdersByUserId(user.getUserID());
        model.addAttribute("orders", orders);
        
        java.util.Map<String, java.util.List<String>> orderImages = new java.util.HashMap<>();
        java.util.Map<String, String> orderProductNames = new java.util.HashMap<>();
        java.util.Map<String, Double> orderTotals = new java.util.HashMap<>();
        for (Order order : orders) {
            orderImages.put(order.getOrderID(), orderService.getProductImagesForOrder(order.getOrderID()));
            java.util.List<String> names = orderService.getProductNamesForOrder(order.getOrderID());
            orderProductNames.put(order.getOrderID(), String.join(", ", names));
            orderTotals.put(order.getOrderID(), orderService.getOrderTotalValue(order.getOrderID()));
        }
        model.addAttribute("orderImages", orderImages);
        model.addAttribute("orderProductNames", orderProductNames);
        model.addAttribute("orderTotals", orderTotals);
        return "orders/user-orders";
    }

    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam("orderID") String orderID, 
                                    @RequestParam("status") String status,
                                    jakarta.servlet.http.HttpSession session,
                                    org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        com.mycompany.techstore.model.User user = (com.mycompany.techstore.model.User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole().name())) {
            return "redirect:/";
        }

        Order order = orderService.getOrderById(orderID);
        if (order != null) {
            order.setOrderStatus(com.mycompany.techstore.model.OrderStatus.valueOf(status));
            orderService.updateOrder(order);
            redirectAttributes.addFlashAttribute("successMessage", "Đã cập nhật trạng thái đơn hàng thành công!");
        }
        return "redirect:/orders/list";
    }
}
