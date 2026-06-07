package com.mycompany.techstore.factory.payment;

import com.mycompany.techstore.model.Order;

public interface PaymentProcessor {
    String processPayment(Order order, double amount);
}
