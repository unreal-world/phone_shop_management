package com.mycompany.phonestore.model;

public class StoreStatisticsReportData {
    private int totalOrders;
    private double totalRevenue;
    private int totalProductsStock;
    private int totalProductTypes;

    // Order status counts
    private int processingCount;
    private int deliveringCount;
    private int deliveredCount;
    private int cancelledCount;

    // Order status revenue
    private double processingRevenue;
    private double deliveringRevenue;
    private double deliveredRevenue;
    private double cancelledRevenue;

    public StoreStatisticsReportData() {}

    public StoreStatisticsReportData(int totalOrders, double totalRevenue, int totalProductsStock, int totalProductTypes,
                                      int processingCount, int deliveringCount, int deliveredCount, int cancelledCount,
                                      double processingRevenue, double deliveringRevenue, double deliveredRevenue, double cancelledRevenue) {
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.totalProductsStock = totalProductsStock;
        this.totalProductTypes = totalProductTypes;
        this.processingCount = processingCount;
        this.deliveringCount = deliveringCount;
        this.deliveredCount = deliveredCount;
        this.cancelledCount = cancelledCount;
        this.processingRevenue = processingRevenue;
        this.deliveringRevenue = deliveringRevenue;
        this.deliveredRevenue = deliveredRevenue;
        this.cancelledRevenue = cancelledRevenue;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTotalProductsStock() {
        return totalProductsStock;
    }

    public void setTotalProductsStock(int totalProductsStock) {
        this.totalProductsStock = totalProductsStock;
    }

    public int getTotalProductTypes() {
        return totalProductTypes;
    }

    public void setTotalProductTypes(int totalProductTypes) {
        this.totalProductTypes = totalProductTypes;
    }

    public int getProcessingCount() {
        return processingCount;
    }

    public void setProcessingCount(int processingCount) {
        this.processingCount = processingCount;
    }

    public int getDeliveringCount() {
        return deliveringCount;
    }

    public void setDeliveringCount(int deliveringCount) {
        this.deliveringCount = deliveringCount;
    }

    public int getDeliveredCount() {
        return deliveredCount;
    }

    public void setDeliveredCount(int deliveredCount) {
        this.deliveredCount = deliveredCount;
    }

    public int getCancelledCount() {
        return cancelledCount;
    }

    public void setCancelledCount(int cancelledCount) {
        this.cancelledCount = cancelledCount;
    }

    public double getProcessingRevenue() {
        return processingRevenue;
    }

    public void setProcessingRevenue(double processingRevenue) {
        this.processingRevenue = processingRevenue;
    }

    public double getDeliveringRevenue() {
        return deliveringRevenue;
    }

    public void setDeliveringRevenue(double deliveringRevenue) {
        this.deliveringRevenue = deliveringRevenue;
    }

    public double getDeliveredRevenue() {
        return deliveredRevenue;
    }

    public void setDeliveredRevenue(double deliveredRevenue) {
        this.deliveredRevenue = deliveredRevenue;
    }

    public double getCancelledRevenue() {
        return cancelledRevenue;
    }

    public void setCancelledRevenue(double cancelledRevenue) {
        this.cancelledRevenue = cancelledRevenue;
    }
}
