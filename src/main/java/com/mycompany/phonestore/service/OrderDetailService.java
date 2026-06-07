package com.mycompany.phonestore.service;

import java.util.List;

import com.mycompany.phonestore.model.OrderDetail;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetails();
    OrderDetail getOrderDetailById(String orderDetailID);
    List<OrderDetail> getOrderDetailsByOrderId(String orderID);
    void saveOrderDetail(OrderDetail orderDetail);
    void updateOrderDetail(OrderDetail orderDetail);
    void deleteOrderDetail(String orderDetailID);
}
