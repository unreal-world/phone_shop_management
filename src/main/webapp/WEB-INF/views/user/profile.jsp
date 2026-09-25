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
                    <label for="newPassword">Mật khẩu mới</label>
                    <div class="password-wrapper">
                        <input type="password" name="newPassword" id="newPassword" placeholder="Nhập mật khẩu mới" />
                        <button type="button" class="toggle-password-btn" onclick="togglePassword('newPassword', this)" title="Hiện mật khẩu" aria-label="Hiện mật khẩu">
                            <svg class="eye-icon eye-open" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z"/>
                                <circle cx="12" cy="12" r="3"/>
                            </svg>
                            <svg class="eye-icon eye-close" style="display: none;" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M9.88 9.88a3 3 0 1 0 4.24 4.24"/>
                                <path d="M10.73 5.08A10.43 10.43 0 0 1 12 5c7 0 10 7 10 7a13.16 13.16 0 0 1-1.67 2.68"/>
                                <path d="M6.61 6.61A13.526 13.526 0 0 0 2 12s3 7 10 7a9.74 9.74 0 0 0 5.39-1.61"/>
                                <line x1="2" x2="22" y1="2" y2="22"/>
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="form-group">
                    <label for="confirmNewPassword">Xác nhận mật khẩu</label>
                    <div class="password-wrapper">
                        <input type="password" name="confirmNewPassword" id="confirmNewPassword" placeholder="Nhập lại mật khẩu" />
                        <button type="button" class="toggle-password-btn" onclick="togglePassword('confirmNewPassword', this)" title="Hiện mật khẩu" aria-label="Hiện mật khẩu">
                            <svg class="eye-icon eye-open" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z"/>
                                <circle cx="12" cy="12" r="3"/>
                            </svg>
                            <svg class="eye-icon eye-close" style="display: none;" xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M9.88 9.88a3 3 0 1 0 4.24 4.24"/>
                                <path d="M10.73 5.08A10.43 10.43 0 0 1 12 5c7 0 10 7 10 7a13.16 13.16 0 0 1-1.67 2.68"/>
                                <path d="M6.61 6.61A13.526 13.526 0 0 0 2 12s3 7 10 7a9.74 9.74 0 0 0 5.39-1.61"/>
                                <line x1="2" x2="22" y1="2" y2="22"/>
                            </svg>
                        </button>
                    </div>
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
        function togglePassword(inputId, btn) {
            var input = document.getElementById(inputId);
            if (!input) return;
            var isPassword = input.type === 'password';
            input.type = isPassword ? 'text' : 'password';
            
            var eyeOpen = btn.querySelector('.eye-open');
            var eyeClose = btn.querySelector('.eye-close');
            if (eyeOpen && eyeClose) {
                eyeOpen.style.display = isPassword ? 'none' : 'block';
                eyeClose.style.display = isPassword ? 'block' : 'none';
            }
            btn.setAttribute('aria-label', isPassword ? 'Ẩn mật khẩu' : 'Hiện mật khẩu');
            btn.setAttribute('title', isPassword ? 'Ẩn mật khẩu' : 'Hiện mật khẩu');
        }

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
