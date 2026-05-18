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

    @GetMapping("/list")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
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
        model.addAttribute("orders", orderService.getOrdersByUserId(userID));
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
        model.addAttribute("orders", orderService.getOrdersByUserId(user.getUserID()));
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
