package com.mycompany.phonestore.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.phonestore.dao.OrderDetailDao;
import com.mycompany.phonestore.model.OrderDetail;

@Repository
public class OrderDetailDaoImpl implements OrderDetailDao {

    private final JdbcTemplate jdbcTemplate;

    public OrderDetailDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        String sql = "SELECT * FROM OrderDetail";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new OrderDetail(
                rs.getString("orderDetailID"),
                rs.getString("orderID"),
                rs.getString("productID"),
                rs.getInt("quantity"),
                rs.getDouble("unitPrice"),
                rs.getDouble("totalPrice")
        ));
    }

    @Override
    public OrderDetail getOrderDetailById(String orderDetailID) {
        String sql = "SELECT * FROM OrderDetail WHERE orderDetailID = ?";
        List<OrderDetail> orderDetails = jdbcTemplate.query(sql, new Object[]{orderDetailID}, (rs, rowNum) -> new OrderDetail(
                rs.getString("orderDetailID"),
                rs.getString("orderID"),
                rs.getString("productID"),
                rs.getInt("quantity"),
                rs.getDouble("unitPrice"),
                rs.getDouble("totalPrice")
        ));
        return orderDetails.isEmpty() ? null : orderDetails.get(0);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderID) {
        String sql = "SELECT * FROM OrderDetail WHERE orderID = ?";
        return jdbcTemplate.query(sql, new Object[]{orderID}, (rs, rowNum) -> new OrderDetail(
                rs.getString("orderDetailID"),
                rs.getString("orderID"),
                rs.getString("productID"),
                rs.getInt("quantity"),
                rs.getDouble("unitPrice"),
                rs.getDouble("totalPrice")
        ));
    }

    @Override
    public void addOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO OrderDetail (orderDetailID, orderID, productID, quantity, unitPrice, totalPrice) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderDetail.getOrderDetailID(), orderDetail.getOrderID(), orderDetail.getProductID(), orderDetail.getQuantity(), orderDetail.getUnitPrice(), orderDetail.getTotalPrice());
    }

    @Override
    public void updateOrderDetail(OrderDetail orderDetail) {
        String sql = "UPDATE OrderDetail SET orderID=?, productID=?, quantity=?, unitPrice=?, totalPrice=? WHERE orderDetailID=?";
        jdbcTemplate.update(sql, orderDetail.getOrderID(), orderDetail.getProductID(), orderDetail.getQuantity(), orderDetail.getUnitPrice(), orderDetail.getTotalPrice(), orderDetail.getOrderDetailID());
    }

    @Override
    public void deleteOrderDetail(String orderDetailID) {
        String sql = "DELETE FROM OrderDetail WHERE orderDetailID=?";
        jdbcTemplate.update(sql, orderDetailID);
    }
}
