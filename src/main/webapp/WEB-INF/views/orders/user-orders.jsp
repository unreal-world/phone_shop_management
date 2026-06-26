<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đơn hàng của tôi</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/orders.css?v=1.2">
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
                    <th style="width: 80px;">Hình ảnh sản phẩm</th>
                    <th style="width: 250px;">Tên sản phẩm</th>
                    <th style="width: 200px;">Giá trị đơn hàng</th>
                    <th>Ngày đặt</th>
                    <th>Người nhận</th>
                    <th>SĐT</th>
                    <th>Địa chỉ</th>
                    <th>Trạng thái</th>
                </tr>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>
                            <div style="display: flex; gap: 5px; justify-content: center; flex-wrap: wrap;">
                                <c:forEach var="imgSrc" items="${orderImages[order.orderID]}">
                                    <img src="${imgSrc}" style="width: 35px !important; height: 35px !important; object-fit: cover; border-radius: 4px; border: 1px solid #ddd;" alt="Sản phẩm" />
                                </c:forEach>
                            </div>
                        </td>
                        <td style="text-align: left; padding: 10px 15px;">
                            <c:forEach var="name" items="${orderProductNames[order.orderID]}">
                                <div style="margin-bottom: 4px; font-size: 0.95em; color: #333;">${name}</div>
                            </c:forEach>
                        </td>
                        <td style="font-weight: bold; line-height: 1.5; text-align: left; padding: 10px 15px;">
                            <c:choose>
                                <c:when test="${order.discount > 0}">
                                    <div style="font-size: 0.85em; color: #777; font-weight: normal;">
                                        Giá gốc: <span style="text-decoration: line-through;"><fmt:formatNumber value="${orderTotals[order.orderID]}" pattern="#,##0"/> VNĐ</span>
                                    </div>
                                    <div style="font-size: 0.85em; color: #28a745; font-weight: normal;">
                                        Giảm giá: -<fmt:formatNumber value="${order.discount}" pattern="#,##0"/> VNĐ
                                    </div>
                                    <div style="color: #e44d26; margin-top: 3px;">
                                        Tổng: <fmt:formatNumber value="${order.finalTotal}" pattern="#,##0"/> VNĐ
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div style="color: #e44d26; text-align: center;">
                                        <fmt:formatNumber value="${order.finalTotal > 0 ? order.finalTotal : orderTotals[order.orderID]}" pattern="#,##0"/> VNĐ
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${order.orderDate}</td>
                        <td>${order.receiver}</td>
                        <td>${order.phoneNumber}</td>
                        <td>${order.fullShippingAddress}</td>
                        <td class="status-${order.orderStatus}">${order.orderStatus}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
