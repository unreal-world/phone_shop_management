package com.mycompany.phonestore.factory.payment;

import com.mycompany.phonestore.model.Order;

public interface PaymentProcessor {
    String processPayment(Order order, double amount);
}
