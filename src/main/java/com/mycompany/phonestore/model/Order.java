package com.mycompany.phonestore.model;

import java.time.LocalDateTime;

public class Order {
    private String orderID;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private String receiver;
    private String phoneNumber;
    private String userID;
    private double discount;
    private double finalTotal;

    public Order() {}

    public Order(String orderID, LocalDateTime orderDate, OrderStatus orderStatus, Address address, String receiver, String phoneNumber, String userID) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.userID = userID;
    }

    public Order(String orderID, LocalDateTime orderDate, OrderStatus orderStatus, Address address, String receiver, String phoneNumber, String userID, double discount, double finalTotal) {
        this(orderID, orderDate, orderStatus, address, receiver, phoneNumber, userID);
        this.discount = discount;
        this.finalTotal = finalTotal;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFullShippingAddress() {
        if (address != null) {
            return address.getHouseNumber() + ", " + address.getStreet() + ", " + address.getWard() + ", " + address.getCity();
        }
        return "N/A";
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(double finalTotal) {
        this.finalTotal = finalTotal;
    }
}
