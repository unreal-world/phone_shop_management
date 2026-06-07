package com.mycompany.phonestore.factory.payment;

public class BankTransferPaymentProcessorCreator implements PaymentProcessorCreator {
    @Override
    public PaymentProcessor createPaymentProcessor() {
        return new BankTransferPaymentProcessor();
    }
}
