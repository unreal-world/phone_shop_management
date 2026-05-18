<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đơn hàng của tôi</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: center; }
        th { background-color: #f2f2f2; color: #333; }
        .status-PENDING { color: #ffc107; font-weight: bold; }
        .status-PROCESSING { color: #17a2b8; font-weight: bold; }
        .status-COMPLETED { color: #28a745; font-weight: bold; }
        .status-FAILED { color: #dc3545; font-weight: bold; }
        .status-CANCELLED { color: #6c757d; font-weight: bold; }
        .empty-orders { text-align: center; font-size: 18px; color: #666; margin-top: 50px; }
        .btn-view { display: inline-block; padding: 6px 12px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; }
    </style>
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
                <a href="${pageContext.request.contextPath}/products/list" style="color: #007bff;">Bắt đầu mua sắm ngay</a>
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
