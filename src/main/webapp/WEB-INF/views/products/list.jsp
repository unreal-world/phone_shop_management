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
        <c:if test="${sessionScope.loggedInUser != null && sessionScope.loggedInUser.role != 'ADMIN'}">
            <span style="margin: 0 15px;">|</span>
            <a href="${pageContext.request.contextPath}/cart" style="text-decoration: none; color: #ff8c00; font-weight: bold;">🛒 Giỏ hàng của bạn</a>
        </c:if>
    </div>

    <c:if test="${not empty successMessage}">
        <div style="color: green; margin-bottom: 15px; padding: 10px; border: 1px solid green; background-color: #d4edda; border-radius: 4px;">
            ${successMessage}
        </div>
    </c:if>

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
            <c:if test="${sessionScope.loggedInUser != null}">
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
                <c:if test="${sessionScope.loggedInUser != null}">
                    <td>
                        <c:choose>
                            <c:when test="${sessionScope.loggedInUser.role == 'ADMIN'}">
                                <a href="edit/${p.productID}" class="action-link edit-link">Sửa</a> | 
                                <a href="delete/${p.productID}" class="action-link delete-link" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')">Xóa</a>
                            </c:when>
                            <c:otherwise>
                                <form action="${pageContext.request.contextPath}/cart/add" method="post" style="margin: 0; display: inline-flex; align-items: center; gap: 5px;">
                                    <input type="hidden" name="productId" value="${p.productID}">
                                    <input type="number" name="quantity" value="1" min="1" max="${p.stock_quantity}" style="width: 50px; padding: 4px;">
                                    <button type="submit" class="btn-add" style="padding: 6px 12px; font-size: 14px; background-color: #007bff; border: none; cursor: pointer;">Thêm vào giỏ</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</body>
</html>