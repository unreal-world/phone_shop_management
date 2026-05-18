<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý đơn hàng</title>
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
        .btn-update { display: inline-block; padding: 6px 12px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; }
        select { padding: 5px; }
    </style>
</head>
<body>
    <h2>Quản lý toàn bộ đơn hàng (Admin)</h2>
    
    <div style="margin-bottom: 20px;">
        <a href="${pageContext.request.contextPath}/" style="text-decoration: none; color: #007bff; font-weight: bold;">← Quay lại trang chủ</a>
    </div>

    <c:if test="${not empty successMessage}">
        <div style="color: green; margin-bottom: 15px; padding: 10px; border: 1px solid green; background-color: #d4edda; border-radius: 4px;">
            ${successMessage}
        </div>
    </c:if>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="empty-orders">
                <p>Hệ thống chưa có đơn hàng nào.</p>
            </div>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>Mã đơn hàng</th>
                    <th>User ID</th>
                    <th>Ngày đặt</th>
                    <th>Người nhận</th>
                    <th>SĐT</th>
                    <th>Địa chỉ</th>
                    <th>Trạng thái hiện tại</th>
                    <th>Cập nhật trạng thái</th>
                </tr>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.orderID}</td>
                        <td>${order.userID}</td>
                        <td>${order.orderDate}</td>
                        <td>${order.receiver}</td>
                        <td>${order.phoneNumber}</td>
                        <td>${order.shippingAddress}</td>
                        <td class="status-${order.orderStatus}">${order.orderStatus}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/orders/update-status" method="post" style="margin: 0; display: inline-flex; align-items: center; gap: 5px;">
                                <input type="hidden" name="orderID" value="${order.orderID}">
                                <select name="status">
                                    <option value="PENDING" ${order.orderStatus == 'PENDING' ? 'selected' : ''}>PENDING</option>
                                    <option value="PROCESSING" ${order.orderStatus == 'PROCESSING' ? 'selected' : ''}>PROCESSING</option>
                                    <option value="COMPLETED" ${order.orderStatus == 'COMPLETED' ? 'selected' : ''}>COMPLETED</option>
                                    <option value="CANCELLED" ${order.orderStatus == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
                                    <option value="FAILED" ${order.orderStatus == 'FAILED' ? 'selected' : ''}>FAILED</option>
                                </select>
                                <button type="submit" class="btn-update">Lưu</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
