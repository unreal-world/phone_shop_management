<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý tài khoản - Tech Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>
<body>
    <div class="profile-container">
        <div class="profile-header">
            <div class="avatar">👤</div>
            <h2>${user.fullName != null && !user.fullName.isEmpty() ? user.fullName : user.username}</h2>
            <span class="username-badge">@${user.username}</span>
        </div>

        <c:if test="${not empty error}">
            <div class="message-error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="message-success">${success}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/users/profile/update" method="post" id="profileForm">

            <div class="section-title">Thông tin cá nhân</div>

            <div class="form-group">
                <label>Tên đăng nhập</label>
                <input type="text" value="${user.username}" readonly />
            </div>

            <div class="form-group">
                <label>Họ và tên</label>
                <input type="text" name="fullName" placeholder="Nhập họ và tên"
                       value="${user.fullName}" />
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" placeholder="example@email.com"
                           value="${user.email}" />
                </div>
                <div class="form-group">
                    <label>Số điện thoại</label>
                    <input type="text" name="phoneNumber" placeholder="0123 456 789"
                           value="${user.phoneNumber}" />
                </div>
            </div>

            <div class="section-title">Đổi mật khẩu</div>

            <div class="form-row">
                <div class="form-group">
                    <label>Mật khẩu mới</label>
                    <input type="password" name="newPassword" id="newPassword" placeholder="Nhập mật khẩu mới" />
                </div>
                <div class="form-group">
                    <label>Xác nhận mật khẩu</label>
                    <input type="password" name="confirmNewPassword" id="confirmNewPassword" placeholder="Nhập lại mật khẩu" />
                </div>
            </div>
            <p class="hint-text">Để trống nếu không muốn đổi mật khẩu.</p>

            <button type="submit" class="btn-save">💾 Lưu thay đổi</button>
        </form>

        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/">← Trang chủ</a>
            <a href="${pageContext.request.contextPath}/auth/logout">Đăng xuất →</a>
        </div>
    </div>

    <script>
        document.getElementById('profileForm').addEventListener('submit', function(e) {
            var newPass = document.getElementById('newPassword').value;
            var confirmPass = document.getElementById('confirmNewPassword').value;

            if (newPass !== '' && newPass !== confirmPass) {
                e.preventDefault();
                alert('Mật khẩu xác nhận không khớp!');
            }
        });
    </script>
</body>
</html>
