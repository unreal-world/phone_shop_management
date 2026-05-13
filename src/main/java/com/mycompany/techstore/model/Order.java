package com.mycompany.techstore.model;

import java.time.LocalDateTime;

public class Order {
    private String orderID;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private String shippingAddress;
    private String receiver;
    private String phoneNumber;
    private String userID;

    public Order() {}

    public Order(String orderID, LocalDateTime orderDate, OrderStatus orderStatus, String shippingAddress, String receiver, String phoneNumber, String userID) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.shippingAddress = shippingAddress;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.userID = userID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
