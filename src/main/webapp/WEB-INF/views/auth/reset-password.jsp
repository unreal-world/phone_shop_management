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
                    <div class="password-wrapper">
                        <input type="password" id="newPassword" name="newPassword"
                               placeholder="Nhập mật khẩu mới" required />
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
                    <label for="confirmPassword">Xác nhận mật khẩu <span class="required">*</span></label>
                    <div class="password-wrapper">
                        <input type="password" id="confirmPassword" name="confirmPassword"
                               placeholder="Nhập lại mật khẩu mới" required />
                        <button type="button" class="toggle-password-btn" onclick="togglePassword('confirmPassword', this)" title="Hiện mật khẩu" aria-label="Hiện mật khẩu">
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
                    <div class="client-error" id="passwordError" style="display: none; color: #e74c3c; font-size: 0.85rem; margin-top: 5px;">Mật khẩu không khớp!</div>
                </div>

                <button type="submit" class="btn-login">Đặt lại mật khẩu</button>
            </form>

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
