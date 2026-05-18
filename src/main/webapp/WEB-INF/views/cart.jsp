<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng của bạn</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .cart-container { display: flex; gap: 20px; margin-top: 20px; }
        .cart-items { flex: 2; }
        .checkout-form { flex: 1; padding: 20px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9; height: fit-content; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: center; }
        th { background-color: #f2f2f2; color: #333; }
        .btn-update { background-color: #ffc107; color: #000; border: none; padding: 6px 12px; cursor: pointer; border-radius: 4px; }
        .btn-remove { background-color: #dc3545; color: white; text-decoration: none; padding: 6px 12px; border-radius: 4px; display: inline-block; }
        .btn-checkout { background-color: #28a745; color: white; border: none; padding: 12px 20px; font-size: 16px; cursor: pointer; border-radius: 4px; width: 100%; margin-top: 15px; font-weight: bold; }
        .form-group { margin-bottom: 15px; text-align: left; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .form-group input { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .total-price { font-size: 20px; font-weight: bold; color: #dc3545; margin-top: 15px; text-align: right; }
        .empty-cart { text-align: center; font-size: 18px; color: #666; margin-top: 50px; }
    </style>
</head>
<body>
    <h2>Giỏ hàng của bạn</h2>
    
    <div style="margin-bottom: 20px;">
        <a href="${pageContext.request.contextPath}/" style="text-decoration: none; color: #007bff; font-weight: bold;">← Tiếp tục mua sắm</a>
    </div>

    <c:if test="${not empty errorMessage}">
        <div style="color: red; margin-bottom: 15px; padding: 10px; border: 1px solid red; background-color: #f8d7da; border-radius: 4px;">
            ${errorMessage}
        </div>
    </c:if>

    <c:choose>
        <c:when test="${empty cartItems}">
            <div class="empty-cart">
                <p>Giỏ hàng của bạn đang trống.</p>
                <a href="${pageContext.request.contextPath}/" style="color: #007bff;">Đến trang sản phẩm</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="cart-container">
                <div class="cart-items">
                    <table>
                        <tr>
                            <th>Tên sản phẩm</th>
                            <th>Đơn giá</th>
                            <th>Số lượng</th>
                            <th>Thành tiền</th>
                            <th>Hành động</th>
                        </tr>
                        <c:forEach var="entry" items="${cartItems}">
                            <tr>
                                <td>${entry.key.productName}</td>
                                <td>${entry.key.price}</td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/cart/update" method="post" style="margin: 0; display: inline-flex; align-items: center; gap: 5px;">
                                        <input type="hidden" name="productId" value="${entry.key.productID}">
                                        <input type="number" name="quantity" value="${entry.value}" min="1" max="${entry.key.stock_quantity}" style="width: 60px; padding: 4px;">
                                        <button type="submit" class="btn-update">Cập nhật</button>
                                    </form>
                                </td>
                                <td>${entry.key.price * entry.value}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/cart/remove/${entry.key.productID}" class="btn-remove" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?')">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="total-price">
                        Tổng cộng: ${total} VNĐ
                    </div>
                </div>

                <div class="checkout-form">
                    <h3>Thông tin thanh toán</h3>
                    <form action="${pageContext.request.contextPath}/cart/checkout" method="post">
                        <div class="form-group">
                            <label for="receiver">Người nhận hàng:</label>
                            <input type="text" id="receiver" name="receiver" value="${sessionScope.loggedInUser != null && sessionScope.loggedInUser.fullName != null && !sessionScope.loggedInUser.fullName.isEmpty() ? sessionScope.loggedInUser.fullName : sessionScope.loggedInUser.username}" required>
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber">Số điện thoại:</label>
                            <input type="text" id="phoneNumber" name="phoneNumber" value="${sessionScope.loggedInUser.phoneNumber}" required>
                        </div>
                        <div class="form-group">
                            <label for="shippingAddress">Địa chỉ giao hàng:</label>
                            <input type="text" id="shippingAddress" name="shippingAddress" required placeholder="Nhập địa chỉ chi tiết...">
                        </div>
                        <button type="submit" class="btn-checkout">Xác nhận thanh toán</button>
                    </form>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</body>
</html>
