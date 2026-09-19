<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head><title>Products - SriramMart</title></head>
<body>
    <h1>Browse Products</h1>
    <p><a href="${pageContext.request.contextPath}/">Home</a></p>

    <form method="get" action="${pageContext.request.contextPath}/products">
        <input type="text" name="category" placeholder="Category" value="${param.category}">
        <input type="text" name="keyword" placeholder="Search keyword" value="${param.keyword}">
        <button type="submit">Search</button>
    </form>

    <table border="1" cellpadding="6">
        <tr><th>Name</th><th>Category</th><th>Price</th><th>Stock</th><th></th></tr>
        <c:forEach var="p" items="${products}">
            <tr>
                <td><c:out value="${p.name}"/></td>
                <td><c:out value="${p.category}"/></td>
                <td><fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₹"/></td>
                <td><c:out value="${p.quantity}"/></td>
                <td><a href="${pageContext.request.contextPath}/products/view?id=${p.id}">View</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>