package com.mycompany.phonestore.dao;

public interface StatisticsDao {
    int getTotalOrders();
    double getTotalRevenue();
    int getTotalProductsStock();
    int getTotalProductTypes();
    int getOrdersCountByStatus(String status);
    double getRevenueByStatus(String status);
}
