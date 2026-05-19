<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đơn hàng của tôi</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/orders.css?v=1.1">
</head>
<body>
    <h2>Đơn hàng của tôi</h2>
    
    <div style="margin-bottom: 20px;">
        <a href="${pageContext.request.contextPath}/" style="text-decoration: none; color: #007bff; font-weight: bold;">← Quay lại trang chủ</a>
    </div>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="empty-orders">
                <p>Bạn chưa có đơn hàng nào.</p>
                <a href="${pageContext.request.contextPath}/" style="color: #007bff;">Bắt đầu mua sắm ngay</a>
            </div>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>Mã đơn hàng</th>
                    <th>Ngày đặt</th>
                    <th>Người nhận</th>
                    <th>SĐT</th>
                    <th>Địa chỉ</th>
                    <th>Trạng thái</th>
                </tr>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.orderID}</td>
                        <td>${order.orderDate}</td>
                        <td>${order.receiver}</td>
                        <td>${order.phoneNumber}</td>
                        <td>${order.shippingAddress}</td>
                        <td class="status-${order.orderStatus}">${order.orderStatus}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
