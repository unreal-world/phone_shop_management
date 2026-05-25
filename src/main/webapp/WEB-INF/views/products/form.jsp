<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin sản phẩm - Tech Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/product-form.css?v=1.1">
</head>
<body>
    <div class="product-form-container">
        <h2>🛠️ Thông tin sản phẩm</h2>
        
        <form:form action="${pageContext.request.contextPath}/products/save" method="post" modelAttribute="product" enctype="multipart/form-data">
            <form:hidden path="productID" />
            
            <div class="form-group">
                <label for="productName">Tên sản phẩm <span class="required">*</span></label>
                <form:input path="productName" id="productName" required="required" placeholder="Nhập tên sản phẩm" />
            </div>
            
            <div class="form-group">
                <label for="brand">Hãng sản xuất</label>
                <form:input path="brand" id="brand" placeholder="Nhập hãng sản xuất" />
            </div>
            
            <div class="form-group">
                <label for="price">Giá bán (VNĐ) <span class="required">*</span></label>
                <form:input path="price" id="price" type="number" step="0.01" required="required" placeholder="Nhập giá bán" />
            </div>
            
            <div class="form-group">
                <label for="description">Mô tả sản phẩm</label>
                <form:textarea path="description" id="description" placeholder="Nhập mô tả chi tiết sản phẩm..." rows="4" />
            </div>
            
            <div class="form-group">
                <label for="stock_quantity">Số lượng tồn kho <span class="required">*</span></label>
                <form:input path="stock_quantity" id="stock_quantity" type="number" required="required" placeholder="Nhập số lượng trong kho" />
            </div>
            
            <div class="form-group">
                <label for="productImage">Hình ảnh sản phẩm</label>
                <input type="file" name="productImage" id="productImage" accept="image/*" />
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn-save">💾 Lưu sản phẩm</button>
                <a href="${pageContext.request.contextPath}/" class="btn-cancel">← Hủy</a>
            </div>
        </form:form>
    </div>
</body>
</html>