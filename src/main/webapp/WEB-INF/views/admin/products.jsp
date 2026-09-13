<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Products - Admin</title></head>
<body>
    <h1>All Products</h1>
    <table border="1" cellpadding="6">
        <tr><th>Name</th><th>Seller ID</th><th>Price</th><th></th></tr>
        <c:forEach var="p" items="${products}">
            <tr>
                <td><c:out value="${p.name}"/></td>
                <td><c:out value="${p.sellerId}"/></td>
                <td><c:out value="${p.price}"/></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin/products/delete">
                        <input type="hidden" name="id" value="${p.id}">
                        <button type="submit">Remove Listing</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>