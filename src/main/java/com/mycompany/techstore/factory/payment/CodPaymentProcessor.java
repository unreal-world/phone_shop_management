package com.mycompany.techstore.factory.payment;

import com.mycompany.techstore.model.Order;

public class CodPaymentProcessor implements PaymentProcessor {
    @Override
    public String processPayment(Order order, double amount) {
        return "Quý khách vui lòng thanh toán số tiền " + String.format("%,.0f", amount) + " VNĐ cho shipper khi nhận hàng.";
    }
}
