<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/orders.css?v=1.1">
</head>
<body>
    <h2>Quản lý toàn bộ đơn hàng (Admin)</h2>
    
    <div style="margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 10px;">
        <a href="${pageContext.request.contextPath}/" style="text-decoration: none; color: #007bff; font-weight: bold; font-size: 15px;">← Quay lại trang chủ</a>
    </div>

    <%-- Thanh tìm kiếm và sắp xếp --%>
    <div style="margin-bottom: 25px; background: #fff; padding: 15px; border-radius: 8px; box-shadow: 0 2px 6px rgba(0,0,0,0.06); border: 1px solid #e0e0e0;">
        <form action="${pageContext.request.contextPath}/orders/list" method="get" style="display: flex; gap: 12px; align-items: center; flex-wrap: wrap;">
            <input type="text" name="keyword" value="${keyword}" placeholder="🔍 Tìm kiếm theo tên khách hàng hoặc tên tài khoản..." 
                   style="flex: 1; min-width: 260px; padding: 10px 14px; border: 1px solid #ccc; border-radius: 6px; font-size: 14px;">
            
            <%-- Dropbox sắp xếp theo tên khách hàng --%>
            <select name="sort" onchange="this.form.submit()" id="sortSelect"
                    style="padding: 10px 14px; border: 1px solid #ccc; border-radius: 6px; background: #fff; cursor: pointer; font-size: 14px;">
                <option value="" ${empty sort ? 'selected' : ''}>-- Sắp xếp theo tên khách hàng --</option>
                <option value="name_asc"  ${'name_asc'  == sort ? 'selected' : ''}>Tên khách hàng: A → Z</option>
                <option value="name_desc" ${'name_desc' == sort ? 'selected' : ''}>Tên khách hàng: Z → A</option>
            </select>

            <button type="submit" style="padding: 10px 22px; background-color: #007bff; color: white; border: none; border-radius: 6px; cursor: pointer; font-weight: 600; font-size: 14px;">Tìm kiếm</button>
            
            <c:if test="${not empty keyword or not empty sort}">
                <a href="${pageContext.request.contextPath}/orders/list" style="padding: 10px 18px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 6px; font-size: 14px; font-weight: 500;">Xóa lọc</a>
            </c:if>
        </form>
    </div>

    <c:if test="${not empty successMessage}">
        <div style="color: green; margin-bottom: 15px; padding: 10px; border: 1px solid green; background-color: #d4edda; border-radius: 4px;">
            ${successMessage}
        </div>
    </c:if>

    <c:choose>
        <c:when test="${empty groupedOrders}">
            <div class="empty-orders" style="padding: 40px 20px; background: #fff; border-radius: 8px; border: 1px dashed #ccc; margin-top: 20px; text-align: center;">
                <c:choose>
                    <c:when test="${not empty keyword}">
                        <p style="font-size: 16px; color: #555; margin: 0 0 10px;">Không tìm thấy đơn hàng nào phù hợp với từ khóa: "<strong>${keyword}</strong>".</p>
                        <a href="${pageContext.request.contextPath}/orders/list" style="color: #007bff; text-decoration: none; font-weight: 600;">Xem tất cả đơn hàng</a>
                    </c:when>
                    <c:otherwise>
                        <p style="font-size: 16px; color: #666; margin: 0;">Hệ thống chưa có đơn hàng nào.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="entry" items="${groupedOrders}">
                <div style="background-color: #f8f9fa; padding: 10px; border-left: 5px solid #007bff; margin-top: 30px; border-radius: 4px;">
                    <h3 style="margin: 0; color: #333;"> Khách hàng: ${entry.key.fullName != null && !entry.key.fullName.isEmpty() ? entry.key.fullName : entry.key.username} 
                        <span style="font-size: 14px; font-weight: normal; color: #666;">(Tài khoản: ${entry.key.username})</span>
                    </h3>
                    <p style="margin: 5px 0 0 0; font-size: 14px; color: #666;">
                        Email: ${entry.key.email != null ? entry.key.email : 'N/A'} | SĐT: ${entry.key.phoneNumber != null ? entry.key.phoneNumber : 'N/A'}
                    </p>
                </div>
                
                <table>
                    <tr>
                        <th style="width: 80px;">Mã đơn hàng</th>
                        <th style="width: 80px;">Hình ảnh sản phẩm</th>
                        <th style="width: 250px;">Tên sản phẩm</th>
                        <th style="width: 200px;">Giá trị đơn hàng</th>
                        <th>Ngày đặt</th>
                        <th>Người nhận</th>
                        <th>SĐT giao hàng</th>
                        <th>Địa chỉ giao hàng</th>
                        <th>Trạng thái hiện tại</th>
                        <th>Cập nhật trạng thái</th>
                    </tr>
                    <c:forEach var="order" items="${entry.value}">
                        <tr>
                            <td>${order.orderID}</td>
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
                            <td>
                                <form action="${pageContext.request.contextPath}/orders/update-status" method="post" style="margin: 0; display: inline-flex; align-items: center; gap: 5px;">
                                    <input type="hidden" name="orderID" value="${order.orderID}">
                                    <select name="status">
                                        <option value="PROCESSING" ${order.orderStatus == 'PROCESSING' ? 'selected' : ''}>PROCESSING</option>
                                        <option value="DELIVERING" ${order.orderStatus == 'DELIVERING' ? 'selected' : ''}>DELIVERING</option>
                                        <option value="DELIVERED" ${order.orderStatus == 'DELIVERED' ? 'selected' : ''}>DELIVERED</option>
                                        <option value="CANCELLED" ${order.orderStatus == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
                                    </select>
                                    <button type="submit" class="btn-update">Lưu</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</body>
</html>
