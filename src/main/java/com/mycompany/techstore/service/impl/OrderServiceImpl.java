package com.mycompany.techstore.service.impl;

import com.mycompany.techstore.dao.OrderDao;
import com.mycompany.techstore.model.Order;
import com.mycompany.techstore.service.OrderService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public Order getOrderById(String orderID) {
        return orderDao.getOrderById(orderID);
    }

    @Override
    public List<Order> getOrdersByUserId(String userID) {
        return orderDao.getOrdersByUserId(userID);
    }

    @Override
    public List<String> getProductImagesForOrder(String orderID) {
        return orderDao.getProductImagesForOrder(orderID);
    }

    @Override
    public void saveOrder(Order order) {
        orderDao.addOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public void deleteOrder(String orderID) {
        orderDao.deleteOrder(orderID);
    }
}
