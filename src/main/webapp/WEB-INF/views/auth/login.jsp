<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Đăng nhập - Phone Store</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
        </head>

        <body>
            <div class="login-container">
                <h2>🔐 Đăng nhập</h2>
                <p class="subtitle">Chào mừng trở lại Phone Store</p>

                <c:if test="${not empty error}">
                    <div class="message-error">${error}</div>
                </c:if>

                <c:if test="${not empty success}">
                    <div class="message-success">${success}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/auth/login" method="post">
                    <div class="form-group">
                        <label for="username">Tên đăng nhập</label>
                        <input type="text" id="username" name="username" placeholder="Nhập tên đăng nhập"
                            value="${username}" required />
                    </div>
                    <div class="form-group">
                        <label for="password">Mật khẩu</label>
                        <input type="password" id="password" name="password" placeholder="Nhập mật khẩu" required />
                    </div>
                    <button type="submit" class="btn-login">Đăng nhập</button>
                </form>

                <div class="bottom-links">
                    Chưa có tài khoản?
                    <a href="${pageContext.request.contextPath}/auth/register">Đăng ký ngay</a>
                </div>

                <div class="home-link">
                    <a href="${pageContext.request.contextPath}/">← Quay về trang chủ</a>
                </div>
            </div>
        </body>

        </html>