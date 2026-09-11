<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên mật khẩu - Phone Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>

<body>
    <div class="login-container">
        <h2>🔑 Quên mật khẩu</h2>
        <p class="subtitle">Nhập email để nhận link đặt lại mật khẩu</p>

        <c:if test="${not empty error}">
            <div class="message-error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="message-success">${success}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/auth/forgot-password" method="post">
            <div class="form-group">
                <label for="email">Email <span class="required">*</span></label>
                <input type="email" id="email" name="email"
                       placeholder="example@email.com" required />
            </div>
            <button type="submit" class="btn-login">Gửi yêu cầu</button>
        </form>

        <div class="bottom-links">
            <a href="${pageContext.request.contextPath}/auth/login">← Quay lại đăng nhập</a>
        </div>

        <div class="home-link">
            <a href="${pageContext.request.contextPath}/">← Quay về trang chủ</a>
        </div>
    </div>
</body>

</html>
