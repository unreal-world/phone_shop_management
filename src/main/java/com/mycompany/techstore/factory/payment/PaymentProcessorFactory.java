package com.mycompany.techstore.factory.payment;

public class PaymentProcessorFactory {
    public static PaymentProcessorCreator getCreator(String paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ.");
        }
        switch (paymentMethod.toUpperCase()) {
            case "COD":
                return new CodPaymentProcessorCreator();
            case "BANK_TRANSFER":
                return new BankTransferPaymentProcessorCreator();
            case "MOMO":
                return new MomoPaymentProcessorCreator();
            default:
                throw new IllegalArgumentException("Không hỗ trợ phương thức thanh toán: " + paymentMethod);
        }
    }
}
