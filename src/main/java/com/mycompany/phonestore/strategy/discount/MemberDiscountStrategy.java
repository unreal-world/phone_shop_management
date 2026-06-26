package com.mycompany.phonestore.strategy.discount;

/**
 * Concrete Strategy: Ưu đãi giảm giá tự động dành cho thành viên đã đăng ký.
 * Áp dụng mức giảm cố định 5% trên tổng hóa đơn.
 * Chiến lược này được kích hoạt tự động khi người dùng có role USER
 * và không nhập mã giảm giá nào.
 */
public class MemberDiscountStrategy implements DiscountStrategy {

    private static final double MEMBER_DISCOUNT_PERCENT = 5.0;

    @Override
    public double applyDiscount(double originalTotal) {
        return originalTotal * (1 - MEMBER_DISCOUNT_PERCENT / 100.0);
    }

    @Override
    public String getDescription() {
        return "Ưu đãi thành viên: Giảm " + (int) MEMBER_DISCOUNT_PERCENT + "%";
    }
}
