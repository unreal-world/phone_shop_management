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
        <p class="subtitle">Xác nhận gửi email đặt lại mật khẩu</p>

        <c:if test="${not empty error}">
            <div class="message-error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="message-success">${success}</div>
        </c:if>

        <c:choose>
            <c:when test="${not empty email}">
                <div style="background: rgba(255, 255, 255, 0.06); padding: 18px 20px; border-radius: 12px; margin-bottom: 24px; border: 1px solid rgba(255, 255, 255, 0.12);">
                    <div style="margin-bottom: 10px; color: rgba(255, 255, 255, 0.85); font-size: 14px;">
                        <span style="color: rgba(255, 255, 255, 0.6);">Tên tài khoản:</span> 
                        <strong style="color: #a78bfa; font-size: 15px; margin-left: 6px;">${username}</strong>
                    </div>
                    <div style="color: rgba(255, 255, 255, 0.85); font-size: 14px;">
                        <span style="color: rgba(255, 255, 255, 0.6);">Email đăng ký:</span> 
                        <strong style="color: #34d399; font-size: 15px; margin-left: 6px;">${email}</strong>
                    </div>
                </div>

                <c:if test="${empty success}">
                    <form action="${pageContext.request.contextPath}/auth/forgot-password" method="post">
                        <input type="hidden" name="username" value="${username}" />
                        <input type="hidden" name="email" value="${email}" />
                        <button type="submit" class="btn-login">📧 Gửi email</button>
                    </form>
                </c:if>
            </c:when>
            <c:otherwise>
                <c:if test="${empty success}">
                    <form action="${pageContext.request.contextPath}/auth/forgot-password" method="get">
                        <div class="form-group">
                            <label for="username">Tên đăng nhập <span class="required">*</span></label>
                            <input type="text" id="username" name="username"
                                   placeholder="Nhập tên đăng nhập của bạn" value="${username}" required />
                        </div>
                        <button type="submit" class="btn-login">Tiếp tục</button>
                    </form>
                </c:if>
            </c:otherwise>
        </c:choose>

        <div class="bottom-links">
            <a href="${pageContext.request.contextPath}/auth/login">← Quay lại đăng nhập</a>
        </div>

        <div class="home-link">
            <a href="${pageContext.request.contextPath}/">← Quay về trang chủ</a>
        </div>
    </div>
</body>

</html>
