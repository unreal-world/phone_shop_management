package com.mycompany.techstore.dao;

import java.util.List;

import com.mycompany.techstore.model.Order;

public interface OrderDao {
    List<Order> getAllOrders();
    Order getOrderById(String orderID);
    List<Order> getOrdersByUserId(String userID);
    List<String> getProductImagesForOrder(String orderID);
    List<String> getProductNamesForOrder(String orderID);
    double getOrderTotalValue(String orderID);
    void addOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(String orderID);
}
