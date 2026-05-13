package com.mycompany.techstore.controller;

import com.mycompany.techstore.model.OrderDetail;
import com.mycompany.techstore.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/list")
    public String listOrderDetails(Model model) {
        model.addAttribute("orderDetails", orderDetailService.getAllOrderDetails());
        return "order-details/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("orderDetail", new OrderDetail());
        return "order-details/form";
    }

    @PostMapping("/save")
    public String saveOrderDetail(@ModelAttribute("orderDetail") OrderDetail orderDetail) {
        if (orderDetail.getOrderDetailID() == null) {
            orderDetailService.saveOrderDetail(orderDetail);
        } else {
            orderDetailService.updateOrderDetail(orderDetail);
        }
        return "redirect:/order-details/list";
    }

    @GetMapping("/edit/{orderDetailID}")
    public String showEditForm(@PathVariable("orderDetailID") String orderDetailID, Model model) {
        model.addAttribute("orderDetail", orderDetailService.getOrderDetailById(orderDetailID));
        return "order-details/form";
    }

    @GetMapping("/delete/{orderDetailID}")
    public String deleteOrderDetail(@PathVariable("orderDetailID") String orderDetailID) {
        orderDetailService.deleteOrderDetail(orderDetailID);
        return "redirect:/order-details/list";
    }

    @GetMapping("/order/{orderID}")
    public String getOrderDetailsByOrder(@PathVariable("orderID") String orderID, Model model) {
        model.addAttribute("orderDetails", orderDetailService.getOrderDetailsByOrderId(orderID));
        return "order-details/order-details";
    }
}
