<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Edit Product</title></head>
<body>
    <h1>Edit Product</h1>
    <form method="post" action="${pageContext.request.contextPath}/seller/products/update">
        <input type="hidden" name="id" value="${product.id}">
        <input type="text" name="name" value="${product.name}" required><br>
        <textarea name="description">${product.description}</textarea><br>
        <input type="number" step="0.01" name="price" value="${product.price}" required><br>
        <input type="number" name="quantity" value="${product.quantity}" required><br>
        <input type="text" name="category" value="${product.category}" required><br>
        <input type="text" name="imageUrl" value="${product.imageUrl}"><br>
        <button type="submit">Save Changes</button>
    </form>
</body>
</html>