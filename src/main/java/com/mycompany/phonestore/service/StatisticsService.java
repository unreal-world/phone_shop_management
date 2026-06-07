package com.mycompany.phonestore.service;

public interface StatisticsService {
    int getTotalOrders();
    double getTotalRevenue();
    int getTotalProductsStock();
    int getTotalProductTypes();
    int getOrdersCountByStatus(String status);
    double getRevenueByStatus(String status);
}
