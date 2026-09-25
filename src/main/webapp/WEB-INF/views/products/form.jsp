<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty product.productID ? 'Thêm sản phẩm mới' : 'Cập nhật sản phẩm'} - Phone Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/product-form.css?v=1.2">
</head>
<body>
    <c:set var="isAdd" value="${empty product.productID or product.productID == ''}" />

    <div class="product-form-container">
        <h2>${isAdd ? '➕ Thêm sản phẩm mới' : '🛠️ Cập nhật sản phẩm'}</h2>
        
        <c:if test="${not empty error}">
            <div class="message-error">${error}</div>
        </c:if>
        
        <form:form action="${pageContext.request.contextPath}/products/save" method="post" modelAttribute="product" enctype="multipart/form-data" id="productForm">
            <form:hidden path="productID" id="productID" />
            
            <div class="form-group">
                <label for="productName">Tên sản phẩm <span class="required">*</span></label>
                <form:input path="productName" id="productName" required="required" placeholder="Nhập tên sản phẩm" />
            </div>
            
            <div class="form-group">
                <label for="brand">Hãng sản xuất <span class="required">*</span></label>
                <form:input path="brand" id="brand" required="required" placeholder="Nhập hãng sản xuất (VD: Apple, Samsung...)" />
            </div>
            
            <div class="form-group">
                <label for="price">Giá bán (VNĐ) <span class="required">*</span></label>
                <form:input path="price" id="price" type="number" step="0.01" required="required" placeholder="Nhập giá bán" />
            </div>
            
            <div class="form-group">
                <label for="description">Mô tả sản phẩm <span class="required">*</span></label>
                <form:textarea path="description" id="description" required="required" placeholder="Nhập mô tả chi tiết sản phẩm..." rows="4" />
            </div>
            
            <div class="form-group">
                <label for="stock_quantity">Số lượng tồn kho <span class="required">*</span></label>
                <form:input path="stock_quantity" id="stock_quantity" type="number" required="required" placeholder="Nhập số lượng trong kho" />
            </div>
            
            <div class="form-group">
                <label for="productImage">Hình ảnh sản phẩm <span class="required" id="imageRequiredMark"><c:if test="${isAdd}">*</c:if></span></label>
                <input type="file" name="productImage" id="productImage" accept="image/*" <c:if test="${isAdd}">required="required"</c:if> />
                <c:if test="${not isAdd}">
                    <p class="hint-text" style="color: rgba(255, 255, 255, 0.5); font-size: 13px; margin-top: 5px;">(Để trống nếu không muốn thay đổi ảnh hiện tại)</p>
                </c:if>
                <div class="client-error" id="imageError">Vui lòng chọn hình ảnh sản phẩm!</div>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn-save">💾 ${isAdd ? 'Thêm sản phẩm' : 'Lưu thay đổi'}</button>
                <a href="${pageContext.request.contextPath}/" class="btn-cancel">← Hủy</a>
            </div>
        </form:form>
    </div>

    <script>
        document.getElementById('productForm').addEventListener('submit', function(e) {
            var brandInput = document.getElementById('brand');
            var descInput = document.getElementById('description');
            var imageInput = document.getElementById('productImage');
            var isAdd = ${isAdd ? 'true' : 'false'};
            var imageError = document.getElementById('imageError');

            if (brandInput && !brandInput.value.trim()) {
                e.preventDefault();
                alert('Vui lòng nhập hãng sản xuất!');
                brandInput.focus();
                return;
            }

            if (descInput && !descInput.value.trim()) {
                e.preventDefault();
                alert('Vui lòng nhập mô tả sản phẩm!');
                descInput.focus();
                return;
            }

            if (isAdd && (!imageInput || !imageInput.files || imageInput.files.length === 0)) {
                e.preventDefault();
                if (imageError) {
                    imageError.style.display = 'block';
                }
                alert('Vui lòng chọn hình ảnh sản phẩm!');
                imageInput.focus();
                return;
            }
        });
    </script>
</body>
</html>