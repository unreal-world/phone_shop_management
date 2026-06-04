package com.mycompany.techstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.techstore.dao.StatisticsDao;
import com.mycompany.techstore.service.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsDao statisticsDao;

    @Override
    public int getTotalOrders() {
        return statisticsDao.getTotalOrders();
    }

    @Override
    public double getTotalRevenue() {
        return statisticsDao.getTotalRevenue();
    }

    @Override
    public int getTotalProductsStock() {
        return statisticsDao.getTotalProductsStock();
    }

    @Override
    public int getTotalProductTypes() {
        return statisticsDao.getTotalProductTypes();
    }

    @Override
    public int getOrdersCountByStatus(String status) {
        return statisticsDao.getOrdersCountByStatus(status);
    }

    @Override
    public double getRevenueByStatus(String status) {
        return statisticsDao.getRevenueByStatus(status);
    }
}
