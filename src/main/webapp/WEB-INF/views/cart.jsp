<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng của bạn</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css?v=1.1">
</head>
<body>
    <h2>Giỏ hàng của bạn</h2>
    
    <div style="margin-bottom: 20px;">
        <a href="${pageContext.request.contextPath}/" style="text-decoration: none; color: #007bff; font-weight: bold;">← Tiếp tục mua sắm</a>
    </div>

    <c:if test="${not empty errorMessage}">
        <div style="color: red; margin-bottom: 15px; padding: 10px; border: 1px solid red; background-color: #f8d7da; border-radius: 4px;">
            ${errorMessage}
        </div>
    </c:if>

    <c:choose>
        <c:when test="${empty cartItems}">
            <div class="empty-cart">
                <p>Giỏ hàng của bạn đang trống.</p>
                <a href="${pageContext.request.contextPath}/" style="color: #007bff;">Đến trang sản phẩm</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="cart-container">
                <div class="cart-items">
                    <table>
                        <tr>
                            <th>Hình ảnh</th>
                            <th>Tên sản phẩm</th>
                            <th>Đơn giá</th>
                            <th>Số lượng</th>
                            <th>Thành tiền</th>
                            <th>Hành động</th>
                        </tr>
                        <c:forEach var="entry" items="${cartItems}">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty entry.key.primaryImage}">
                                            <img src="${entry.key.primaryImage}" alt="${entry.key.productName}" style="max-width: 60px; height: 60px; object-fit: contain; border-radius: 4px;">
                                        </c:when>
                                        <c:otherwise>
                                            <div style="width: 60px; height: 60px; background-color: #f5f5f5; display: flex; align-items: center; justify-content: center; color: #999; border-radius: 4px; font-size: 10px;">Không có ảnh</div>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${entry.key.productName}</td>
                                <td><fmt:formatNumber value="${entry.key.price}" pattern="#,##0"/></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/cart/update" method="post" style="margin: 0; display: inline-flex; align-items: center; gap: 5px;">
                                        <input type="hidden" name="productId" value="${entry.key.productID}">
                                        <input type="number" name="quantity" value="${entry.value}" min="1" max="${entry.key.stock_quantity}" style="width: 60px; padding: 4px;">
                                        <button type="submit" class="btn-update">Cập nhật</button>
                                    </form>
                                </td>
                                <td><fmt:formatNumber value="${entry.key.price * entry.value}" pattern="#,##0"/></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/cart/remove/${entry.key.productID}" class="btn-remove" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?')">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="total-price">
                        Tổng cộng: <fmt:formatNumber value="${total}" pattern="#,##0"/> VNĐ
                    </div>
                </div>

                <div class="checkout-form">
                    <h3>Thông tin thanh toán</h3>
                    <form action="${pageContext.request.contextPath}/cart/checkout" method="post">
                        <div class="form-group">
                            <label for="receiver">Người nhận hàng:</label>
                            <input type="text" id="receiver" name="receiver" value="${sessionScope.loggedInUser != null && sessionScope.loggedInUser.fullName != null && !sessionScope.loggedInUser.fullName.isEmpty() ? sessionScope.loggedInUser.fullName : sessionScope.loggedInUser.username}" required>
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber">Số điện thoại:</label>
                            <input type="text" id="phoneNumber" name="phoneNumber" value="${sessionScope.loggedInUser.phoneNumber}" required>
                        </div>
                        <div class="form-group">
                            <label>Địa chỉ giao hàng:</label>
                            
                            <!-- Hiển thị danh sách địa chỉ cũ nếu có -->
                            <c:if test="${not empty userAddresses}">
                                <div style="margin-bottom: 15px; border: 1px solid #ddd; padding: 10px; border-radius: 4px; background-color: #fafafa;">
                                    <p style="margin-top: 0; font-weight: bold; margin-bottom: 10px;">Chọn địa chỉ đã lưu:</p>
                                    <c:forEach var="addr" items="${userAddresses}" varStatus="status">
                                        <div style="margin-bottom: 8px;">
                                            <input type="radio" id="address_${addr.addressID}" name="selectedAddressId" value="${addr.addressID}" ${status.first ? 'checked' : ''} onchange="toggleNewAddressForm()">
                                            <label for="address_${addr.addressID}" style="font-weight: normal; margin-left: 5px; cursor: pointer;">
                                                ${addr.houseNumber}, ${addr.street}, ${addr.ward}, ${addr.city}
                                            </label>
                                        </div>
                                    </c:forEach>
                                    <div style="margin-bottom: 5px; margin-top: 10px; padding-top: 10px; border-top: 1px solid #eee;">
                                        <input type="radio" id="address_new" name="selectedAddressId" value="new" onchange="toggleNewAddressForm()">
                                        <label for="address_new" style="font-weight: bold; margin-left: 5px; color: #007bff; cursor: pointer;">
                                            + Nhập địa chỉ mới
                                        </label>
                                    </div>
                                </div>
                            </c:if>

                            <!-- Nếu không có địa chỉ cũ nào, mặc định là địa chỉ mới -->
                            <c:if test="${empty userAddresses}">
                                <input type="hidden" name="selectedAddressId" value="new">
                            </c:if>

                            <!-- Form nhập địa chỉ mới (sẽ bị ẩn nếu chọn địa chỉ cũ) -->
                               <div id="newAddressForm"
                                   <c:if test="${not empty userAddresses}">style="display: none;"</c:if>>
                                <div style="margin-bottom: 10px;">
                                    <input type="text" name="city" placeholder="Thành phố/Tỉnh" style="width: 100%; padding: 10px; margin-bottom: 8px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;" ${empty userAddresses ? 'required' : ''}>
                                    <input type="text" name="ward" placeholder="Phường/Xã" style="width: 100%; padding: 10px; margin-bottom: 8px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;" ${empty userAddresses ? 'required' : ''}>
                                    <input type="text" name="street" placeholder="Tên đường" style="width: 100%; padding: 10px; margin-bottom: 8px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;" ${empty userAddresses ? 'required' : ''}>
                                    <input type="text" name="houseNumber" placeholder="Số nhà" style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;" ${empty userAddresses ? 'required' : ''}>
                                </div>
                            </div>
                        </div>

                        <script>
                            function toggleNewAddressForm() {
                                var newAddressRadio = document.getElementById("address_new");
                                var newAddressForm = document.getElementById("newAddressForm");
                                var inputs = newAddressForm.querySelectorAll("input");
                                
                                if (!newAddressRadio || newAddressRadio.checked) {
                                    newAddressForm.style.display = "block";
                                    inputs.forEach(function(input) { input.required = true; });
                                } else {
                                    newAddressForm.style.display = "none";
                                    inputs.forEach(function(input) { input.required = false; });
                                }
                            }

                            function previewDiscount() {
                                var code = document.getElementById('discountCode').value.trim();
                                var rawTotal = ${total};
                                var resultDiv = document.getElementById('discountResult');

                                fetch('${pageContext.request.contextPath}/cart/apply-discount'
                                    + '?code=' + encodeURIComponent(code)
                                    + '&rawTotal=' + rawTotal)
                                    .then(function(res) { return res.json(); })
                                    .then(function(data) {
                                        resultDiv.style.display = 'block';
                                        if (data.saved > 0) {
                                            resultDiv.style.backgroundColor = '#d4edda';
                                            resultDiv.style.color = '#155724';
                                            resultDiv.style.border = '1px solid #c3e6cb';
                                            resultDiv.innerHTML =
                                                '✅ ' + data.description + '<br>'
                                                + 'Tiết kiệm: <strong>'
                                                + data.saved.toLocaleString('vi-VN') + 'đ</strong><br>'
                                                + 'Tổng thanh toán: <strong>'
                                                + data.discountedTotal.toLocaleString('vi-VN') + 'đ</strong>';
                                        } else {
                                            resultDiv.style.backgroundColor = '#fff3cd';
                                            resultDiv.style.color = '#856404';
                                            resultDiv.style.border = '1px solid #ffc107';
                                            resultDiv.innerHTML = 'ℹ️ ' + data.description;
                                        }
                                    })
                                    .catch(function() {
                                        resultDiv.style.display = 'block';
                                        resultDiv.style.backgroundColor = '#f8d7da';
                                        resultDiv.style.color = '#721c24';
                                        resultDiv.style.border = '1px solid #f5c6cb';
                                        resultDiv.innerHTML = '❌ Không thể kiểm tra mã giảm giá.';
                                    });
                            }
                        </script>
                        <div class="form-group">
                            <label for="paymentMethod">Phương thức thanh toán:</label>
                            <select id="paymentMethod" name="paymentMethod" style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; background-color: #fff; font-family: inherit;">
                                <option value="COD">Thanh toán khi nhận hàng (COD)</option>
                                <option value="BANK_TRANSFER">Chuyển khoản ngân hàng</option>
                                <option value="MOMO">Ví điện tử MoMo</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="discountCode">Mã giảm giá (nếu có):</label>
                            <div style="display: flex; gap: 8px;">
                                <input type="text" id="discountCode" name="discountCode"
                                       placeholder="VD: SALE10, GIAM200K..."
                                       style="flex: 1; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-family: inherit;">
                                <button type="button" onclick="previewDiscount()"
                                        style="padding: 10px 16px; background: #28a745; color: white;
                                               border: none; border-radius: 4px; cursor: pointer; white-space: nowrap;">
                                    Áp dụng
                                </button>
                            </div>
                            <div id="discountResult" style="display: none; margin-top: 10px;
                                 padding: 10px; border-radius: 4px; font-size: 14px;"></div>
                        </div>
                        <button type="submit" class="btn-checkout">Xác nhận thanh toán</button>
                    </form>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</body>
</html>
