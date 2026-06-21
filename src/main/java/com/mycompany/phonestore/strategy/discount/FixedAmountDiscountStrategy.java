package com.mycompany.phonestore.strategy.discount;

/**
 * Concrete Strategy: Giảm một khoản tiền cố định trên tổng hóa đơn.
 * Ví dụ: discountAmount = 200_000 → giảm 200,000đ.
 * Nếu tổng tiền nhỏ hơn mức giảm, kết quả trả về 0 (không âm).
 */
public class FixedAmountDiscountStrategy implements DiscountStrategy {

    private final double discountAmount;

    public FixedAmountDiscountStrategy(double discountAmount) {
        if (discountAmount < 0) {
            throw new IllegalArgumentException("Số tiền giảm không được âm.");
        }
        this.discountAmount = discountAmount;
    }

    @Override
    public double applyDiscount(double originalTotal) {
        return Math.max(0, originalTotal - discountAmount);
    }

    @Override
    public String getDescription() {
        return "Giảm " + String.format("%,.0f", discountAmount) + "đ trên đơn hàng";
    }
}
