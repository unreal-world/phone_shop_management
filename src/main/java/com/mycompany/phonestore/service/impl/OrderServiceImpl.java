package com.mycompany.phonestore.service.impl;

import com.mycompany.phonestore.dao.OrderDao;
import com.mycompany.phonestore.model.Order;
import com.mycompany.phonestore.service.OrderService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.mycompany.phonestore.observer.ConcreteObserver.AdminObserver;
import com.mycompany.phonestore.observer.ConcreteObserver.CustomerObserver;
import com.mycompany.phonestore.observer.ConcreteSubject.OrderNotificationSubject;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    private final OrderNotificationSubject orderSubject;

    public OrderServiceImpl(OrderDao orderDao) {
    this.orderDao = orderDao;

    this.orderSubject = new OrderNotificationSubject();

    this.orderSubject.addObserver(new AdminObserver());
    this.orderSubject.addObserver(new CustomerObserver());
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
    public List<String> getProductNamesForOrder(String orderID) {
        return orderDao.getProductNamesForOrder(orderID);
    }

    @Override
    public double getOrderTotalValue(String orderID) {
        return orderDao.getOrderTotalValue(orderID);
    }

    @Override
    public void saveOrder(Order order) {

    orderDao.addOrder(order);

    orderSubject.notifyObservers(
        "Đơn hàng mới đã được tạo: " + order.getOrderID()
    );
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
