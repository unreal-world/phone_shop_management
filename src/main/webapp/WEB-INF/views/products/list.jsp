<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: center; }
        th { background-color: #f2f2f2; color: #333; }
        .btn-add { display: inline-block; padding: 10px 20px; background-color: #28a745; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .btn-add:hover { background-color: #218838; }
        .action-link { text-decoration: none; padding: 4px 8px; border-radius: 4px; }
        .edit-link { background-color: #ffc107; color: #000; }
        .delete-link { background-color: #dc3545; color: white; }
    </style>
</head>
<body>
    <h2>Danh sách sản phẩm</h2>
    
    <div style="margin-bottom: 20px;">
        <a href="${pageContext.request.contextPath}/" style="text-decoration: none; color: #007bff; font-weight: bold;">← Quay lại trang chủ</a>
    </div>

    <c:if test="${sessionScope.loggedInUser.role == 'ADMIN'}">
        <div style="margin-bottom: 15px;">
            <a href="add" class="btn-add">+ Thêm sản phẩm mới</a>
        </div>
    </c:if>

    <table>
        <tr>
            <c:if test="${sessionScope.loggedInUser.role == 'ADMIN'}">
                <th>ID</th>
            </c:if>
            <th>Tên sản phẩm</th>
            <th>Hãng</th>
            <th>Giá</th>
            <th>Mô tả</th>
            <th>Tồn kho</th>
            <c:if test="${sessionScope.loggedInUser.role == 'ADMIN'}">
                <th>Hành động</th>
            </c:if>
        </tr>
        <c:forEach var="p" items="${products}">
            <tr>
                <c:if test="${sessionScope.loggedInUser.role == 'ADMIN'}">
                    <td>${p.productID}</td>
                </c:if>
                <td>${p.productName}</td>
                <td>${p.brand}</td>
                <td>${p.price}</td>
                <td>${p.description}</td>
                <td>${p.stock_quantity}</td>
                <c:if test="${sessionScope.loggedInUser.role == 'ADMIN'}">
                    <td>
                        <a href="edit/${p.productID}" class="action-link edit-link">Sửa</a> | 
                        <a href="delete/${p.productID}" class="action-link delete-link" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')">Xóa</a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</body>
</html>