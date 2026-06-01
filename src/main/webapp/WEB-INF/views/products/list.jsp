<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/products.css?v=1.1">
</head>
<body>
    <h2>Danh sách sản phẩm</h2>
    
    <div style="margin-bottom: 20px;">
        <a href="${pageContext.request.contextPath}/" style="text-decoration: none; color: #007bff; font-weight: bold;">← Quay lại trang chủ</a>
        <c:if test="${sessionScope.loggedInUser != null && sessionScope.loggedInUser.role != 'ADMIN'}">
            <span style="margin: 0 15px;">|</span>
            <a href="${pageContext.request.contextPath}/cart" style="text-decoration: none; color: #ff8c00; font-weight: bold;">🛒 Giỏ hàng của bạn</a>
        </c:if>
    </div>

    <c:if test="${not empty successMessage}">
        <div style="color: green; margin-bottom: 15px; padding: 10px; border: 1px solid green; background-color: #d4edda; border-radius: 4px;">
            ${successMessage}
        </div>
    </c:if>

    <c:if test="${sessionScope.loggedInUser.role == 'ADMIN'}">
        <div style="margin-bottom: 15px;">
            <a href="add" class="btn-add">+ Thêm sản phẩm mới</a>
        </div>
    </c:if>

    <div class="product-grid" style="display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 20px;">
        <c:forEach var="p" items="${products}">
            <div class="product-card" style="border: 1px solid #ddd; border-radius: 8px; padding: 15px; text-align: center; background-color: #fff; box-shadow: 0 2px 5px rgba(0,0,0,0.05); position: relative; ${p.isDeleted ? 'opacity: 0.55; filter: grayscale(40%);' : ''}">
                <c:if test="${p.isDeleted}">
                    <div style="background-color: #dc3545; color: white; font-size: 11px; font-weight: bold; padding: 3px 8px; border-radius: 4px; display: inline-block; margin-bottom: 10px; text-transform: uppercase; letter-spacing: 0.5px;">Đã ẩn với khách</div>
                </c:if>
                <c:choose>
                    <c:when test="${not empty p.primaryImage}">
                        <img src="${p.primaryImage}" alt="${p.productName}" style="max-width: 100%; height: 200px; object-fit: contain; margin-bottom: 15px;">
                    </c:when>
                    <c:otherwise>
                        <div style="width: 100%; height: 200px; background-color: #f5f5f5; display: flex; align-items: center; justify-content: center; margin-bottom: 15px; color: #999; border-radius: 4px;">Không có ảnh</div>
                    </c:otherwise>
                </c:choose>
                
                <h3 style="font-size: 18px; margin: 0 0 10px; color: #333; height: 40px; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;">${p.productName}</h3>
                <p style="color: #666; margin: 0 0 5px; font-size: 14px;">Hãng: ${p.brand}</p>
                <p style="color: #e44d26; font-weight: bold; font-size: 18px; margin: 0 0 10px;"><fmt:formatNumber value="${p.price}" pattern="#,##0"/> VNĐ</p>
                <p style="color: #666; margin: 0 0 15px; font-size: 13px;">Còn lại: ${p.stock_quantity}</p>
                
                <div class="product-actions">
                    <c:choose>
                        <c:when test="${sessionScope.loggedInUser.role == 'ADMIN'}">
                            <div style="margin-bottom: 8px; font-size: 11px; color: #999;">ID: ${p.productID}</div>
                            <a href="edit/${p.productID}" class="action-link edit-link" style="display: inline-block; padding: 6px 12px; background: #ffc107; color: #000; text-decoration: none; border-radius: 4px; font-size: 14px; margin-right: 5px;">Sửa</a>
                            <a href="delete/${p.productID}" class="action-link delete-link" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')" style="display: inline-block; padding: 6px 12px; background: #dc3545; color: #fff; text-decoration: none; border-radius: 4px; font-size: 14px;">Xóa</a>
                        </c:when>
                        <c:otherwise>
                            <form action="${pageContext.request.contextPath}/cart/add" method="post" style="margin: 0; display: flex; align-items: center; justify-content: center; gap: 5px;">
                                <input type="hidden" name="productId" value="${p.productID}">
                                <input type="number" name="quantity" value="1" min="1" max="${p.stock_quantity}" style="width: 50px; padding: 5px; border: 1px solid #ccc; border-radius: 4px;">
                                <button type="submit" class="btn-add" style="padding: 6px 12px; font-size: 14px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; transition: background 0.3s;">Thêm vào giỏ</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>