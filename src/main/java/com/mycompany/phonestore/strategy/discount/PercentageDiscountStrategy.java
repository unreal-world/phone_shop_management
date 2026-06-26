package com.mycompany.phonestore.strategy.discount;

/**
 * Concrete Strategy: Giảm giá theo phần trăm trên tổng hóa đơn.
 * Ví dụ: discountPercent = 10.0 → giảm 10%, khách trả 90% tổng tiền.
 */
public class PercentageDiscountStrategy implements DiscountStrategy {

    private final double discountPercent;

    public PercentageDiscountStrategy(double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Phần trăm giảm giá phải từ 0 đến 100.");
        }
        this.discountPercent = discountPercent;
    }

    @Override
    public double applyDiscount(double originalTotal) {
        return originalTotal * (1 - discountPercent / 100.0);
    }

    @Override
    public String getDescription() {
        return "Giảm " + (int) discountPercent + "% trên tổng đơn hàng";
    }
}
