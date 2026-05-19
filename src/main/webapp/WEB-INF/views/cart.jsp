<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng của bạn</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css?v=1.1">
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
