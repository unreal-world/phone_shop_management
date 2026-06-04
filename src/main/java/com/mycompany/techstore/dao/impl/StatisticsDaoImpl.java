package com.mycompany.techstore.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.techstore.dao.StatisticsDao;

@Repository
public class StatisticsDaoImpl implements StatisticsDao {

    private final JdbcTemplate jdbcTemplate;

    public StatisticsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getTotalOrders() {
        String sql = "SELECT COUNT(*) FROM `Order`";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }

    @Override
    public double getTotalRevenue() {
        // Doanh thu từ các đơn hàng không bị hủy (Processing, Delivering, Delivered)
        String sql = "SELECT COALESCE(SUM(od.totalPrice), 0.0) " +
                     "FROM OrderDetail od " +
                     "JOIN `Order` o ON od.orderID = o.orderID " +
                     "WHERE o.orderStatus != 'CANCELLED'";
        Double revenue = jdbcTemplate.queryForObject(sql, Double.class);
        return revenue != null ? revenue : 0.0;
    }

    @Override
    public int getTotalProductsStock() {
        String sql = "SELECT COALESCE(SUM(stock_quantity), 0) FROM Product";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }

    @Override
    public int getTotalProductTypes() {
        String sql = "SELECT COUNT(*) FROM Product";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }

    @Override
    public int getOrdersCountByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM `Order` WHERE orderStatus = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{status}, Integer.class);
        return count != null ? count : 0;
    }

    @Override
    public double getRevenueByStatus(String status) {
        String sql = "SELECT COALESCE(SUM(od.totalPrice), 0.0) " +
                     "FROM OrderDetail od " +
                     "JOIN `Order` o ON od.orderID = o.orderID " +
                     "WHERE o.orderStatus = ?";
        Double revenue = jdbcTemplate.queryForObject(sql, new Object[]{status}, Double.class);
        return revenue != null ? revenue : 0.0;
    }
}
