package com.mycompany.phonestore.service.impl;

import com.mycompany.phonestore.dao.OrderDetailDao;
import com.mycompany.phonestore.model.OrderDetail;
import com.mycompany.phonestore.service.OrderDetailService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailDao orderDetailDao;

    public OrderDetailServiceImpl(OrderDetailDao orderDetailDao) {
        this.orderDetailDao = orderDetailDao;
    }

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailDao.getAllOrderDetails();
    }

    @Override
    public OrderDetail getOrderDetailById(String orderDetailID) {
        return orderDetailDao.getOrderDetailById(orderDetailID);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderID) {
        return orderDetailDao.getOrderDetailsByOrderId(orderID);
    }

    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailDao.addOrderDetail(orderDetail);
    }

    @Override
    public void updateOrderDetail(OrderDetail orderDetail) {
        orderDetailDao.updateOrderDetail(orderDetail);
    }

    @Override
    public void deleteOrderDetail(String orderDetailID) {
        orderDetailDao.deleteOrderDetail(orderDetailID);
    }
}
