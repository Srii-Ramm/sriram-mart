<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>SriramMart</title></head>
<body>
    <h1>SriramMart</h1>

    <c:choose>
        <c:when test="${empty sessionScope.userId}">
            <a href="${pageContext.request.contextPath}/login">Login</a> |
            <a href="${pageContext.request.contextPath}/register">Register</a> |
            <a href="${pageContext.request.contextPath}/products">Browse Products</a>
        </c:when>
        <c:otherwise>
            Welcome, <c:out value="${sessionScope.userName}"/> (<c:out value="${sessionScope.userRole}"/>)
            <br>
            <a href="${pageContext.request.contextPath}/products">Browse Products</a> |
            <c:if test="${sessionScope.userRole == 'BUYER'}">
                <a href="${pageContext.request.contextPath}/cart">Cart</a> |
                <a href="${pageContext.request.contextPath}/orders">My Orders</a> |
            </c:if>
            <c:if test="${sessionScope.userRole == 'SELLER'}">
                <a href="${pageContext.request.contextPath}/seller/products">My Products</a> |
                <a href="${pageContext.request.contextPath}/seller/orders">Incoming Orders</a> |
            </c:if>
            <c:if test="${sessionScope.userRole == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin">Admin Panel</a> |
            </c:if>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:otherwise>
    </c:choose>
</body>
</html>