<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Phone Store - Home</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
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
                            <a href="${pageContext.request.contextPath}/users/profile" class="btn-info">Quản lý tài
                                khoản</a>
                            <a href="${pageContext.request.contextPath}/auth/logout" class="btn-danger">Đăng xuất</a>
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
                        <h2>Hệ thống đã sẵn sàng</h2>
                        <a href="${pageContext.request.contextPath}/products/list" style="text-decoration: none;">
                            <c:choose>
                                <c:when test="${sessionScope.loggedInUser.role == 'ADMIN'}">
                                    <button type="button"
                                        style="padding: 12px 24px; font-size: 18px; cursor: pointer; background-color: #007bff; color: white; border: none; border-radius: 6px; margin-top: 20px;">
                                        ⚙️ Quản lý Sản phẩm
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button"
                                        style="padding: 12px 24px; font-size: 18px; cursor: pointer; background-color: #28a745; color: white; border: none; border-radius: 6px; margin-top: 20px;">
                                        📱 Xem Danh sách Sản phẩm
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </body>

        </html>