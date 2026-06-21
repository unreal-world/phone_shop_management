package com.mycompany.phonestore.strategy.discount;

import com.mycompany.phonestore.model.User;
import com.mycompany.phonestore.model.UserRole;

/**
 * Utility class: Chọn DiscountStrategy phù hợp dựa trên mã giảm giá và thông tin User.
 * Đây là điểm duy nhất trong hệ thống quyết định strategy nào sẽ được sử dụng.
 *
 * Thứ tự ưu tiên:
 *   1. Mã giảm giá hợp lệ (do user nhập)
 *   2. Ưu đãi thành viên tự động (role CUSTOMER, không nhập mã)
 *   3. Không giảm giá (mặc định)
 *
 * Mã giảm giá hợp lệ:
 *   - SALE10   → Giảm 10%
 *   - SALE20   → Giảm 20%
 *   - GIAM200K → Giảm 200,000đ
 *   - GIAM500K → Giảm 500,000đ
 */
public class DiscountStrategySelector {

    public static DiscountStrategy select(String discountCode, User user) {

        // Ưu tiên 1: Mã giảm giá do người dùng nhập
        if (discountCode != null && !discountCode.trim().isEmpty()) {
            switch (discountCode.trim().toUpperCase()) {
                case "SALE10":
                    return new PercentageDiscountStrategy(10.0);
                case "SALE20":
                    return new PercentageDiscountStrategy(20.0);
                case "GIAM200K":
                    return new FixedAmountDiscountStrategy(200_000.0);
                case "GIAM500K":
                    return new FixedAmountDiscountStrategy(500_000.0);
                default:
                    // Mã không hợp lệ → tiếp tục kiểm tra ưu đãi thành viên
                    break;
            }
        }

        // Ưu tiên 2: Ưu đãi thành viên tự động (role CUSTOMER)
        if (user != null && UserRole.CUSTOMER.equals(user.getRole())) {
            return new MemberDiscountStrategy();
        }

        // Mặc định: Không giảm giá
        return new NoDiscountStrategy();
    }
}
