<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Phone Store - Home</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css?v=1.1">
        </head>

        <body>
            <div class="header">
                <h1>Phone Store</h1>
                <div class="nav-buttons">
                    <c:choose>
                        <c:when test="${not empty sessionScope.loggedInUser}">
                            <span>Xin chào, <strong>${sessionScope.loggedInUser.fullName != null &&
                                    !sessionScope.loggedInUser.fullName.isEmpty() ? sessionScope.loggedInUser.fullName :
                                    sessionScope.loggedInUser.username}</strong>!</span>
                            <a href="${pageContext.request.contextPath}/users/profile" class="btn-info">Quản lý tài khoản</a>
                            <c:if test="${sessionScope.loggedInUser.role != 'ADMIN'}">
                                <a href="${pageContext.request.contextPath}/orders/my-orders" class="btn-success" style="background-color: #28a745; margin-left: 10px;">Đơn hàng của tôi</a>
                            </c:if>
                            <c:if test="${sessionScope.loggedInUser.role == 'ADMIN'}">
                                <a href="${pageContext.request.contextPath}/orders/list" class="btn-success" style="background-color: #007bff; margin-left: 10px;">Quản lý Đơn hàng</a>
                                <a href="${pageContext.request.contextPath}/statistics" class="btn-info" style="margin-left: 10px;">Thống kê</a>
                            </c:if>
                            <a href="${pageContext.request.contextPath}/auth/logout" class="btn-danger" style="margin-left: 10px;">Đăng xuất</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/auth/login" class="btn-primary">Đăng nhập</a>
                            <a href="${pageContext.request.contextPath}/auth/register" class="btn-success">Đăng ký</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="main-content">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
                    <h2 style="margin: 0;">Danh sách sản phẩm</h2>
                    <c:if test="${empty sessionScope.loggedInUser || sessionScope.loggedInUser.role != 'ADMIN'}">
                        <a href="${pageContext.request.contextPath}/cart" style="text-decoration: none; color: #ff8c00; font-weight: bold; font-size: 18px;">🛒 Giỏ hàng của bạn</a>
                    </c:if>
                </div>
                
                <div style="margin-bottom: 20px;">
                    <form action="${pageContext.request.contextPath}/" method="get" style="display: flex; gap: 10px; max-width: 500px;">
                        <input type="text" name="keyword" value="${keyword}" placeholder="Tìm kiếm sản phẩm theo tên..." style="flex: 1; padding: 10px; border: 1px solid #ccc; border-radius: 4px;">
                        <button type="submit" style="padding: 10px 20px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer;">Tìm kiếm</button>
                        <c:if test="${not empty keyword}">
                            <a href="${pageContext.request.contextPath}/" style="padding: 10px 20px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 4px;">Xóa lọc</a>
                        </c:if>
                    </form>
                </div>
                
                <c:if test="${not empty successMessage}">
                    <div style="color: green; margin-bottom: 15px; padding: 10px; border: 1px solid green; background-color: #d4edda; border-radius: 4px;">
                        ${successMessage}
                    </div>
                </c:if>

                <c:if test="${not empty errorMessage}">
                    <div style="color: red; margin-bottom: 15px; padding: 10px; border: 1px solid red; background-color: #f8d7da; border-radius: 4px;">
                        ${errorMessage}
                    </div>
                </c:if>

                <c:if test="${not empty sessionScope.loggedInUser && sessionScope.loggedInUser.role == 'ADMIN'}">
                    <div style="margin-bottom: 15px;">
                        <a href="${pageContext.request.contextPath}/products/add" class="btn-add">+ Thêm sản phẩm mới</a>
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
                            <p style="color: #555; margin: 0 0 10px; font-size: 13px; height: 36px; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; line-height: 1.4;" title="${p.description}">Mô tả: ${p.description}</p>
                            <p style="color: #e44d26; font-weight: bold; font-size: 18px; margin: 0 0 10px;"><fmt:formatNumber value="${p.price}" pattern="#,##0"/> VNĐ</p>
                            <p style="color: ${p.stock_quantity == 0 ? '#dc3545' : '#666'}; margin: 0 0 15px; font-size: 13px; font-weight: ${p.stock_quantity == 0 ? 'bold' : 'normal'};">
                                <c:choose>
                                    <c:when test="${p.stock_quantity == 0}">
                                        Hết hàng
                                    </c:when>
                                    <c:otherwise>
                                        Còn lại: ${p.stock_quantity}
                                    </c:otherwise>
                                </c:choose>
                            </p>
                            
                            <div class="product-actions">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.loggedInUser && sessionScope.loggedInUser.role == 'ADMIN'}">
                                        <div style="margin-bottom: 8px; font-size: 11px; color: #999;">ID: ${p.productID}</div>
                                        <a href="${pageContext.request.contextPath}/products/edit/${p.productID}" class="action-link edit-link" style="display: inline-block; padding: 6px 12px; background: #ffc107; color: #000; text-decoration: none; border-radius: 4px; font-size: 14px; margin-right: 5px;">Sửa</a>
                                        <a href="${pageContext.request.contextPath}/products/delete/${p.productID}" class="action-link delete-link" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')" style="display: inline-block; padding: 6px 12px; background: #dc3545; color: #fff; text-decoration: none; border-radius: 4px; font-size: 14px;">Xóa</a>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="${pageContext.request.contextPath}/cart/add" method="post" style="margin: 0; display: flex; align-items: center; justify-content: center; width: 100%;">
                                            <input type="hidden" name="productId" value="${p.productID}">
                                            <input type="hidden" name="quantity" value="1">
                                            <c:choose>
                                                <c:when test="${p.stock_quantity == 0}">
                                                    <button type="button" class="btn-add" disabled style="width: 100%; padding: 8px 12px; font-size: 14px; background-color: #cccccc; color: #666666; border: none; border-radius: 4px; cursor: not-allowed; pointer-events: none;">Thêm vào giỏ hàng</button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button type="submit" class="btn-add" style="width: 100%; padding: 8px 12px; font-size: 14px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; transition: background 0.3s;">Thêm vào giỏ hàng</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </body>

        </html>