<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<body>
    <h2>Danh sách sản phẩm</h2>
    <a href="add">Thêm sản phẩm mới</a>
    <table border="1">
        <tr>
            <th>ID</th><th>Tên</th><th>Hãng</th><th>Giá</th><th>Tồn kho</th><th>Hành động</th>
        </tr>
        <c:forEach var="p" items="${products}">
            <tr>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>${p.brand}</td>
                <td>${p.price}</td>
                <td>${p.stockQuantity}</td>
                <td>
                    <a href="edit/${p.id}">Sửa</a> | 
                    <a href="delete/${p.id}" onclick="return confirm('Xóa?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>