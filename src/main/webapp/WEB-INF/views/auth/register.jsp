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
                            <label>Mật khẩu <span class="required">*</span></label>
                            <input type="password" name="password" id="password" placeholder="Nhập mật khẩu" required />
                        </div>
                        <div class="form-group">
                            <label>Xác nhận mật khẩu <span class="required">*</span></label>
                            <input type="password" name="confirmPassword" id="confirmPassword"
                                placeholder="Nhập lại mật khẩu" required />
                            <div class="client-error" id="passwordError">Mật khẩu không khớp!</div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Họ và tên</label>
                        <input type="text" name="fullName" placeholder="Nhập họ và tên" value="${user.fullName}" />
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" placeholder="example@email.com" value="${user.email}" />
                        </div>
                        <div class="form-group">
                            <label>Số điện thoại</label>
                            <input type="text" name="phoneNumber" placeholder="0123 456 789"
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
                document.getElementById('registerForm').addEventListener('submit', function (e) {
                    var password = document.getElementById('password').value;
                    var confirmPassword = document.getElementById('confirmPassword').value;
                    var errorDiv = document.getElementById('passwordError');

                    if (password !== confirmPassword) {
                        e.preventDefault();
                        errorDiv.style.display = 'block';
                    } else {
                        errorDiv.style.display = 'none';
                    }
                });
            </script>
        </body>

        </html>