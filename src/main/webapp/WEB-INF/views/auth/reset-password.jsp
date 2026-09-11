<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt lại mật khẩu - Phone Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>

<body>
    <div class="login-container">
        <h2>🔒 Đặt lại mật khẩu</h2>
        <p class="subtitle">Nhập mật khẩu mới cho tài khoản của bạn</p>

        <c:if test="${not empty error}">
            <div class="message-error">${error}</div>
        </c:if>

        <c:if test="${not empty token}">
            <form action="${pageContext.request.contextPath}/auth/reset-password" method="post" id="resetForm">
                <input type="hidden" name="token" value="${token}" />

                <div class="form-group">
                    <label for="newPassword">Mật khẩu mới <span class="required">*</span></label>
                    <input type="password" id="newPassword" name="newPassword"
                           placeholder="Nhập mật khẩu mới" required />
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Xác nhận mật khẩu <span class="required">*</span></label>
                    <input type="password" id="confirmPassword" name="confirmPassword"
                           placeholder="Nhập lại mật khẩu mới" required />
                    <div class="client-error" id="passwordError" style="display: none; color: #e74c3c; font-size: 0.85rem; margin-top: 5px;">Mật khẩu không khớp!</div>
                </div>

                <button type="submit" class="btn-login">Đặt lại mật khẩu</button>
            </form>

            <script>
                document.getElementById('resetForm').addEventListener('submit', function (e) {
                    var newPassword = document.getElementById('newPassword').value;
                    var confirmPassword = document.getElementById('confirmPassword').value;
                    var errorDiv = document.getElementById('passwordError');
                    if (newPassword !== confirmPassword) {
                        e.preventDefault();
                        errorDiv.style.display = 'block';
                    } else {
                        errorDiv.style.display = 'none';
                    }
                });
            </script>
        </c:if>

        <div class="bottom-links">
            <a href="${pageContext.request.contextPath}/auth/login">← Quay lại đăng nhập</a>
        </div>

        <div class="home-link">
            <a href="${pageContext.request.contextPath}/">← Quay về trang chủ</a>
        </div>
    </div>
</body>

</html>
