package com.mycompany.techstore.factory.payment;

public class BankTransferPaymentProcessorCreator implements PaymentProcessorCreator {
    @Override
    public PaymentProcessor createPaymentProcessor() {
        return new BankTransferPaymentProcessor();
    }
}
