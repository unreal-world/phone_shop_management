<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
    <body>
        <h2>Thông tin sản phẩm</h2>
        <form:form action="${pageContext.request.contextPath}/products/save" method="post" modelAttribute="product">
            <form:hidden path="productID" />
            Tên sản phẩm: <form:input path="productName" /><br/>
            Hãng: <form:input path="brand" /><br/>
            Giá: <form:input path="price" /><br/>
            Mô tả: <form:textarea path="description" /><br/>
            Số lượng: <form:input path="stock_quantity" /><br/>
            <button type="submit">Lưu sản phẩm</button>
        </form:form>
    </body>
</html>