package com.mycompany.phonestore.strategy.discount;

/**
 * Strategy Pattern – Interface định nghĩa chiến lược giảm giá.
 * Context (CartController) chỉ gọi qua interface này, không phụ thuộc
 * vào bất kỳ Concrete Strategy nào cụ thể.
 */
public interface DiscountStrategy {

    /**
     * Áp dụng giảm giá và trả về tổng tiền sau khi giảm.
     *
     * @param originalTotal Tổng tiền gốc (chưa giảm)
     * @return Tổng tiền sau khi áp dụng chiến lược giảm giá
     */
    double applyDiscount(double originalTotal);

    /**
     * Mô tả ngắn gọn về chiến lược giảm giá.
     * Dùng để hiển thị thông báo cho người dùng.
     *
     * @return Chuỗi mô tả chiến lược
     */
    String getDescription();
}
