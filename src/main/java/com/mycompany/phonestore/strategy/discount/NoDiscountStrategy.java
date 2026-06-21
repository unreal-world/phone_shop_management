package com.mycompany.phonestore.strategy.discount;

/**
 * Concrete Strategy: Không áp dụng giảm giá.
 * Được dùng làm mặc định khi người dùng không nhập mã giảm giá
 * hoặc không thuộc đối tượng được hưởng ưu đãi.
 */
public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public double applyDiscount(double originalTotal) {
        return originalTotal;
    }

    @Override
    public String getDescription() {
        return "Không áp dụng mã giảm giá";
    }
}
