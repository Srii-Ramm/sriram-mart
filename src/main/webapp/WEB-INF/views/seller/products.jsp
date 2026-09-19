<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>My Products - SriramMart</title></head>
<body>
    <p><a href="${pageContext.request.contextPath}/">Home</a></p>
    <h1>My Products</h1>

    <table border="1" cellpadding="6">
        <tr><th>Name</th><th>Price</th><th>Stock</th><th></th></tr>
        <c:forEach var="p" items="${products}">
            <tr>
                <td><c:out value="${p.name}"/></td>
                <td><c:out value="${p.price}"/></td>
                <td><c:out value="${p.quantity}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/seller/products/edit?id=${p.id}">Edit</a>
                    <form method="post" action="${pageContext.request.contextPath}/seller/products/delete" style="display:inline;">
                        <input type="hidden" name="id" value="${p.id}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h3>Add New Product</h3>
    <c:if test="${not empty error}"><p style="color:red;"><c:out value="${error}"/></p></c:if>
    <form method="post" action="${pageContext.request.contextPath}/seller/products/create">
        <input type="text" name="name" placeholder="Name" required><br>
        <textarea name="description" placeholder="Description"></textarea><br>
        <input type="number" step="0.01" name="price" placeholder="Price" required><br>
        <input type="number" name="quantity" placeholder="Stock quantity" required><br>
        <input type="text" name="category" placeholder="Category" required><br>
        <input type="text" name="imageUrl" placeholder="Image URL"><br>
        <button type="submit">Create Product</button>
    </form>
</body>
</html>