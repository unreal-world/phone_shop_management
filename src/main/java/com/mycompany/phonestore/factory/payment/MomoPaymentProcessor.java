package com.mycompany.phonestore.factory.payment;

import com.mycompany.phonestore.model.Order;

public class MomoPaymentProcessor implements PaymentProcessor {
    @Override
    public String processPayment(Order order, double amount) {
        return "Đã xác thực thanh toán trực tuyến " + String.format("%,.0f", amount) + " VNĐ qua ví điện tử MoMo.";
    }
}
