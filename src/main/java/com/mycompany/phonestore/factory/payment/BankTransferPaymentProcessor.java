package com.mycompany.phonestore.factory.payment;

import com.mycompany.phonestore.model.Order;

public class BankTransferPaymentProcessor implements PaymentProcessor {
    @Override
    public String processPayment(Order order, double amount) {
        return "Vui lòng chuyển khoản " + String.format("%,.0f", amount) + " VNĐ qua Techcombank STK: 123456789 (PhoneStore) với nội dung: thanh toan " + order.getOrderID();
    }
}
