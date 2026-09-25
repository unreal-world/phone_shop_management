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
                        <div class="password-wrapper">
                            <input type="password" id="password" name="password" placeholder="Nhập mật khẩu" required />
                            <button type="button" class="toggle-password-btn" onclick="togglePassword('password', this)" title="Hiện mật khẩu" aria-label="Hiện mật khẩu">
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
                    <button type="submit" class="btn-login">Đăng nhập</button>
                </form>

                <div class="bottom-links">
                    Chưa có tài khoản?
                    <a href="${pageContext.request.contextPath}/auth/register">Đăng ký ngay</a>
                </div>

                <div class="bottom-links">
                    <a href="${pageContext.request.contextPath}/auth/forgot-password" onclick="return handleForgotPassword(event)">Quên mật khẩu?</a>
                </div>

                <div class="home-link">
                    <a href="${pageContext.request.contextPath}/">← Quay về trang chủ</a>
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

                function handleForgotPassword(event) {
                    if (event) {
                        event.preventDefault();
                    }
                    var usernameInput = document.getElementById('username');
                    var username = usernameInput ? usernameInput.value.trim() : '';

                    if (!username) {
                        alert('Vui lòng nhập tên đăng nhập trước khi chọn Quên mật khẩu!');
                        if (usernameInput) {
                            usernameInput.focus();
                        }
                        return false;
                    }

                    window.location.href = '${pageContext.request.contextPath}/auth/forgot-password?username=' + encodeURIComponent(username);
                    return false;
                }
            </script>
        </body>

        </html>