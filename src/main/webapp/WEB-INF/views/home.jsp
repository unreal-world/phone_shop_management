<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Phone Store - Home</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
            <style>
                table { width: 100%; border-collapse: collapse; margin-top: 20px; }
                th, td { border: 1px solid #ddd; padding: 12px; text-align: center; }
                th { background-color: #f2f2f2; color: #333; }
                .btn-add { display: inline-block; padding: 10px 20px; background-color: #28a745; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; }
                .btn-add:hover { background-color: #218838; }
                .action-link { text-decoration: none; padding: 4px 8px; border-radius: 4px; }
                .edit-link { background-color: #ffc107; color: #000; }
                .delete-link { background-color: #dc3545; color: white; }
            </style>
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
                <c:choose>
                    <c:when test="${empty sessionScope.loggedInUser}">
                        <h2>Chào mừng đến với Cửa hàng Điện thoại</h2>
                        <p style="color: #666; font-size: 18px;">Vui lòng đăng nhập hoặc đăng ký để tiếp tục trải nghiệm
                            mua sắm.</p>
                    </c:when>
                    <c:otherwise>
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
                            <h2 style="margin: 0;">Danh sách sản phẩm</h2>
                            <c:if test="${sessionScope.loggedInUser.role != 'ADMIN'}">
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

                        <c:if test="${sessionScope.loggedInUser.role == 'ADMIN'}">
                            <div style="margin-bottom: 15px;">
                                <a href="${pageContext.request.contextPath}/products/add" class="btn-add">+ Thêm sản phẩm mới</a>
                            </div>
                        </c:if>

                        <table>
                            <tr>
                                <c:if test="${sessionScope.loggedInUser.role == 'ADMIN'}">
                                    <th>ID</th>
                                </c:if>
                                <th>Tên sản phẩm</th>
                                <th>Hãng</th>
                                <th>Giá</th>
                                <th>Mô tả</th>
                                <th>Tồn kho</th>
                                <th>Hành động</th>
                            </tr>
                            <c:forEach var="p" items="${products}">
                                <tr>
                                    <c:if test="${sessionScope.loggedInUser.role == 'ADMIN'}">
                                        <td>${p.productID}</td>
                                    </c:if>
                                    <td>${p.productName}</td>
                                    <td>${p.brand}</td>
                                    <td>${p.price}</td>
                                    <td>${p.description}</td>
                                    <td>${p.stock_quantity}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${sessionScope.loggedInUser.role == 'ADMIN'}">
                                                <a href="${pageContext.request.contextPath}/products/edit/${p.productID}" class="action-link edit-link">Sửa</a> | 
                                                <a href="${pageContext.request.contextPath}/products/delete/${p.productID}" class="action-link delete-link" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')">Xóa</a>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="${pageContext.request.contextPath}/cart/add" method="post" style="margin: 0; display: inline-flex; align-items: center; gap: 5px;">
                                                    <input type="hidden" name="productId" value="${p.productID}">
                                                    <input type="number" name="quantity" value="1" min="1" max="${p.stock_quantity}" style="width: 50px; padding: 4px;">
                                                    <button type="submit" class="btn-add" style="padding: 6px 12px; font-size: 14px; background-color: #007bff; border: none; cursor: pointer;">Thêm vào giỏ</button>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </body>

        </html>