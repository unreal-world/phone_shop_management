package com.mycompany.techstore.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.techstore.dao.OrderDao;
import com.mycompany.techstore.model.Order;
import com.mycompany.techstore.model.OrderStatus;
import com.mycompany.techstore.model.Address;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final JdbcTemplate jdbcTemplate;

    public OrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT o.*, a.userID AS a_userID, a.city, a.ward, a.street, a.houseNumber " +
                     "FROM `Order` o LEFT JOIN Address a ON o.addressID = a.addressID";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Address address = null;
            if (rs.getString("addressID") != null) {
                address = new Address(
                        rs.getString("addressID"),
                        rs.getString("a_userID"),
                        rs.getString("city"),
                        rs.getString("ward"),
                        rs.getString("street"),
                        rs.getString("houseNumber")
                );
            }
            return new Order(
                    rs.getString("orderID"),
                    rs.getTimestamp("orderDate").toLocalDateTime(),
                    OrderStatus.valueOf(rs.getString("orderStatus")),
                    address,
                    rs.getString("receiver"),
                    rs.getString("phoneNumber"),
                    rs.getString("userID")
            );
        });
    }

    @Override
    public Order getOrderById(String orderID) {
        String sql = "SELECT o.*, a.userID AS a_userID, a.city, a.ward, a.street, a.houseNumber " +
                     "FROM `Order` o LEFT JOIN Address a ON o.addressID = a.addressID WHERE o.orderID = ?";
        List<Order> orders = jdbcTemplate.query(sql, new Object[]{orderID}, (rs, rowNum) -> {
            Address address = null;
            if (rs.getString("addressID") != null) {
                address = new Address(
                        rs.getString("addressID"),
                        rs.getString("a_userID"),
                        rs.getString("city"),
                        rs.getString("ward"),
                        rs.getString("street"),
                        rs.getString("houseNumber")
                );
            }
            return new Order(
                    rs.getString("orderID"),
                    rs.getTimestamp("orderDate").toLocalDateTime(),
                    OrderStatus.valueOf(rs.getString("orderStatus")),
                    address,
                    rs.getString("receiver"),
                    rs.getString("phoneNumber"),
                    rs.getString("userID")
            );
        });
        return orders.isEmpty() ? null : orders.get(0);
    }

    @Override
    public List<Order> getOrdersByUserId(String userID) {
        String sql = "SELECT o.*, a.userID AS a_userID, a.city, a.ward, a.street, a.houseNumber " +
                     "FROM `Order` o LEFT JOIN Address a ON o.addressID = a.addressID WHERE o.userID = ? ORDER BY o.orderDate DESC";
        return jdbcTemplate.query(sql, new Object[]{userID}, (rs, rowNum) -> {
            Address address = null;
            if (rs.getString("addressID") != null) {
                address = new Address(
                        rs.getString("addressID"),
                        rs.getString("a_userID"),
                        rs.getString("city"),
                        rs.getString("ward"),
                        rs.getString("street"),
                        rs.getString("houseNumber")
                );
            }
            return new Order(
                    rs.getString("orderID"),
                    rs.getTimestamp("orderDate").toLocalDateTime(),
                    OrderStatus.valueOf(rs.getString("orderStatus")),
                    address,
                    rs.getString("receiver"),
                    rs.getString("phoneNumber"),
                    rs.getString("userID")
            );
        });
    }

    @Override
    public List<String> getProductImagesForOrder(String orderID) {
        String sql = "SELECT DISTINCT (SELECT i.imageSource FROM Image i WHERE i.productID = od.productID LIMIT 1) as primaryImage " +
                     "FROM OrderDetail od " +
                     "WHERE od.orderID = ?";
        List<String> images = jdbcTemplate.query(sql, new Object[]{orderID}, (rs, rowNum) -> rs.getString("primaryImage"));
        if (images != null) {
            images.removeIf(img -> img == null || img.trim().isEmpty());
        }
        return images;
    }

    @Override
    public List<String> getProductNamesForOrder(String orderID) {
        String sql = "SELECT DISTINCT p.productName " +
                     "FROM OrderDetail od " +
                     "JOIN Product p ON od.productID = p.productID " +
                     "WHERE od.orderID = ?";
        return jdbcTemplate.query(sql, new Object[]{orderID}, (rs, rowNum) -> rs.getString("productName"));
    }

    @Override
    public double getOrderTotalValue(String orderID) {
        String sql = "SELECT COALESCE(SUM(od.totalPrice), 0.0) FROM OrderDetail od WHERE od.orderID = ?";
        Double total = jdbcTemplate.queryForObject(sql, new Object[]{orderID}, Double.class);
        return total != null ? total : 0.0;
    }

    @Override
    public void addOrder(Order order) {
        String sql = "INSERT INTO `Order` (orderID, userID, orderDate, orderStatus, addressID, receiver, phoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String addressID = order.getAddress() != null ? order.getAddress().getAddressID() : null;
        jdbcTemplate.update(sql, order.getOrderID(), order.getUserID(), java.sql.Timestamp.valueOf(order.getOrderDate()), order.getOrderStatus().toString(), addressID, order.getReceiver(), order.getPhoneNumber());
    }

    @Override
    public void updateOrder(Order order) {
        String sql = "UPDATE `Order` SET userID=?, orderDate=?, orderStatus=?, addressID=?, receiver=?, phoneNumber=? WHERE orderID=?";
        String addressID = order.getAddress() != null ? order.getAddress().getAddressID() : null;
        jdbcTemplate.update(sql, order.getUserID(), java.sql.Timestamp.valueOf(order.getOrderDate()), order.getOrderStatus().toString(), addressID, order.getReceiver(), order.getPhoneNumber(), order.getOrderID());
    }

    @Override
    public void deleteOrder(String orderID) {
        String sql = "DELETE FROM `Order` WHERE orderID=?";
        jdbcTemplate.update(sql, orderID);
    }
}
