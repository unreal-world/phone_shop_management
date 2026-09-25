<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Đăng ký - Phone Store</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
        </head>

        <body>
            <div class="register-container">
                <h2>📝 Đăng ký</h2>
                <p class="subtitle">Tạo tài khoản mới tại Phone Store</p>

                <c:if test="${not empty error}">
                    <div class="message-error">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/auth/register" method="post" id="registerForm">
                    <div class="form-group">
                        <label>Tên đăng nhập <span class="required">*</span></label>
                        <input type="text" name="username" placeholder="Nhập tên đăng nhập" value="${user.username}"
                            required />
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="password">Mật khẩu <span class="required">*</span></label>
                            <div class="password-wrapper">
                                <input type="password" name="password" id="password" placeholder="Nhập mật khẩu" required />
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
                        <div class="form-group">
                            <label for="confirmPassword">Xác nhận mật khẩu <span class="required">*</span></label>
                            <div class="password-wrapper">
                                <input type="password" name="confirmPassword" id="confirmPassword"
                                    placeholder="Nhập lại mật khẩu" required />
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
                            <div class="client-error" id="passwordError">Mật khẩu xác nhận không khớp!</div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="fullName">Họ và tên</label>
                        <input type="text" id="fullName" name="fullName" placeholder="Nhập họ và tên" value="${user.fullName}" />
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="email">Email <span class="required">*</span></label>
                            <input type="email" id="email" name="email" placeholder="example@email.com" value="${user.email}" required />
                            <div class="client-error" id="emailError"></div>
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber">Số điện thoại</label>
                            <input type="text" id="phoneNumber" name="phoneNumber" placeholder="0123 456 789"
                                value="${user.phoneNumber}" />
                        </div>
                    </div>

                    <button type="submit" class="btn-register">Đăng ký</button>
                </form>

                <div class="bottom-links">
                    Đã có tài khoản?
                    <a href="${pageContext.request.contextPath}/auth/login">Đăng nhập</a>
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

                document.getElementById('registerForm').addEventListener('submit', function (e) {
                    var password = document.getElementById('password').value;
                    var confirmPassword = document.getElementById('confirmPassword').value;
                    var passwordError = document.getElementById('passwordError');
                    var emailInput = document.getElementById('email');
                    var email = emailInput ? emailInput.value.trim() : '';
                    var emailError = document.getElementById('emailError');
                    var isValid = true;

                    // Reset errors
                    if (passwordError) passwordError.style.display = 'none';
                    if (emailError) {
                        emailError.style.display = 'none';
                        emailError.innerText = '';
                    }

                    // Validate email bắt buộc & định dạng chuẩn
                    var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
                    if (!email) {
                        e.preventDefault();
                        if (emailError) {
                            emailError.innerText = 'Vui lòng nhập địa chỉ email!';
                            emailError.style.display = 'block';
                        }
                        emailInput.focus();
                        isValid = false;
                    } else if (!emailPattern.test(email)) {
                        e.preventDefault();
                        if (emailError) {
                            emailError.innerText = 'Email không đúng định dạng chuẩn (ví dụ: user@example.com)!';
                            emailError.style.display = 'block';
                        }
                        emailInput.focus();
                        isValid = false;
                    }

                    // Validate mật khẩu xác nhận
                    if (password !== confirmPassword) {
                        e.preventDefault();
                        if (passwordError) {
                            passwordError.innerText = 'Mật khẩu xác nhận không khớp!';
                            passwordError.style.display = 'block';
                        }
                        if (isValid) {
                            document.getElementById('confirmPassword').focus();
                        }
                        isValid = false;
                    }
                });
            </script>
        </body>

        </html>