<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head><title>Orders - Admin</title></head>
<body>
    <h1>All Orders</h1>
    <table border="1" cellpadding="6">
        <tr><th>ID</th><th>Buyer ID</th><th>Status</th><th>Total</th></tr>
        <c:forEach var="o" items="${orders}">
            <tr>
                <td><c:out value="${o.id}"/></td>
                <td><c:out value="${o.buyerId}"/></td>
                <td><c:out value="${o.status}"/></td>
                <td><fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₹"/></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>