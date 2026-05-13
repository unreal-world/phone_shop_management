package com.mycompany.techstore.service;

import java.util.List;

import com.mycompany.techstore.model.Order;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(String orderID);
    List<Order> getOrdersByUserId(String userID);
    void saveOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(String orderID);
}
