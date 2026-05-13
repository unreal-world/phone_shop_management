package com.mycompany.techstore.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.techstore.dao.OrderDao;
import com.mycompany.techstore.model.Order;
import com.mycompany.techstore.model.OrderStatus;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final JdbcTemplate jdbcTemplate;

    public OrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM `Order`";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Order(
                rs.getString("orderID"),
                rs.getTimestamp("orderDate").toLocalDateTime(),
                OrderStatus.valueOf(rs.getString("orderStatus")),
                rs.getString("shippingAddress"),
                rs.getString("receiver"),
                rs.getString("phoneNumber"),
                rs.getString("userID")
        ));
    }

    @Override
    public Order getOrderById(String orderID) {
        String sql = "SELECT * FROM `Order` WHERE orderID = ?";
        List<Order> orders = jdbcTemplate.query(sql, new Object[]{orderID}, (rs, rowNum) -> new Order(
                rs.getString("orderID"),
                rs.getTimestamp("orderDate").toLocalDateTime(),
                OrderStatus.valueOf(rs.getString("orderStatus")),
                rs.getString("shippingAddress"),
                rs.getString("receiver"),
                rs.getString("phoneNumber"),
                rs.getString("userID")
        ));
        return orders.isEmpty() ? null : orders.get(0);
    }

    @Override
    public List<Order> getOrdersByUserId(String userID) {
        String sql = "SELECT * FROM `Order` WHERE userID = ?";
        return jdbcTemplate.query(sql, new Object[]{userID}, (rs, rowNum) -> new Order(
                rs.getString("orderID"),
                rs.getTimestamp("orderDate").toLocalDateTime(),
                OrderStatus.valueOf(rs.getString("orderStatus")),
                rs.getString("shippingAddress"),
                rs.getString("receiver"),
                rs.getString("phoneNumber"),
                rs.getString("userID")
        ));
    }

    @Override
    public void addOrder(Order order) {
        String sql = "INSERT INTO `Order` (orderID, userID, orderDate, orderStatus, shippingAddress, receiver, phoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getOrderID(), order.getUserID(), java.sql.Timestamp.valueOf(order.getOrderDate()), order.getOrderStatus().toString(), order.getShippingAddress(), order.getReceiver(), order.getPhoneNumber());
    }

    @Override
    public void updateOrder(Order order) {
        String sql = "UPDATE `Order` SET userID=?, orderDate=?, orderStatus=?, shippingAddress=?, receiver=?, phoneNumber=? WHERE orderID=?";
        jdbcTemplate.update(sql, order.getUserID(), java.sql.Timestamp.valueOf(order.getOrderDate()), order.getOrderStatus().toString(), order.getShippingAddress(), order.getReceiver(), order.getPhoneNumber(), order.getOrderID());
    }

    @Override
    public void deleteOrder(String orderID) {
        String sql = "DELETE FROM `Order` WHERE orderID=?";
        jdbcTemplate.update(sql, orderID);
    }
}
