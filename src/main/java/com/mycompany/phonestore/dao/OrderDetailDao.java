package com.mycompany.phonestore.dao;

import java.util.List;

import com.mycompany.phonestore.model.OrderDetail;

public interface OrderDetailDao {
    List<OrderDetail> getAllOrderDetails();
    OrderDetail getOrderDetailById(String orderDetailID);
    List<OrderDetail> getOrderDetailsByOrderId(String orderID);
    void addOrderDetail(OrderDetail orderDetail);
    void updateOrderDetail(OrderDetail orderDetail);
    void deleteOrderDetail(String orderDetailID);
}
