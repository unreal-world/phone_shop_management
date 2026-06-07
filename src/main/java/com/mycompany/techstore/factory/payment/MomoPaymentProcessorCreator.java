package com.mycompany.techstore.factory.payment;

public class MomoPaymentProcessorCreator implements PaymentProcessorCreator {
    @Override
    public PaymentProcessor createPaymentProcessor() {
        return new MomoPaymentProcessor();
    }
}
