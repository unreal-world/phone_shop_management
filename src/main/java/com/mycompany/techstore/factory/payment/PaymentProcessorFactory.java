package com.mycompany.techstore.factory.payment;

public class PaymentProcessorFactory {
    public static PaymentProcessor getPaymentProcessor(String paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ.");
        }
        switch (paymentMethod.toUpperCase()) {
            case "COD":
                return new CodPaymentProcessor();
            case "BANK_TRANSFER":
                return new BankTransferPaymentProcessor();
            case "MOMO":
                return new MomoPaymentProcessor();
            default:
                throw new IllegalArgumentException("Không hỗ trợ phương thức thanh toán: " + paymentMethod);
        }
    }
}
