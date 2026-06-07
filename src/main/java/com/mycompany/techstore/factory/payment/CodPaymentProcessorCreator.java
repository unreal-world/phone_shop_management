package com.mycompany.techstore.factory.payment;

public class CodPaymentProcessorCreator implements PaymentProcessorCreator {
    @Override
    public PaymentProcessor createPaymentProcessor() {
        return new CodPaymentProcessor();
    }
}
