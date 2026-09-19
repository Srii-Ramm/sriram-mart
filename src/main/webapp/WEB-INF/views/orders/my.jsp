<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head><title>My Orders - SriramMart</title></head>
<body>
    <h1>My Orders</h1>
    <c:if test="${not empty param.placed}"><p style="color:green;">Order #${param.placed} placed successfully!</p></c:if>

    <c:forEach var="ov" items="${orderViews}">
        <div style="border:1px solid #ccc; margin-bottom:10px; padding:8px;">
            <p>Order #${ov.order.id} — Status: <c:out value="${ov.order.status}"/> —
               Total: <fmt:formatNumber value="${ov.order.totalAmount}" type="currency" currencySymbol="₹"/></p>
            <ul>
                <c:forEach var="item" items="${ov.items}">
                    <li><c:out value="${item.productName}"/> x <c:out value="${item.quantity}"/> @
                        <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₹"/></li>
                </c:forEach>
            </ul>
        </div>
    </c:forEach>
</body>
</html>