<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Incoming Orders - SriramMart</title></head>
<body>
    <h1>Incoming Orders</h1>
    <c:forEach var="ov" items="${orderViews}">
        <div style="border:1px solid #ccc; margin-bottom:10px; padding:8px;">
            <p>Order #${ov.order.id} — Status: <c:out value="${ov.order.status}"/></p>
            <ul>
                <c:forEach var="item" items="${ov.items}">
                    <li><c:out value="${item.productName}"/> x <c:out value="${item.quantity}"/></li>
                </c:forEach>
            </ul>
            <form method="post" action="${pageContext.request.contextPath}/seller/orders">
                <input type="hidden" name="orderId" value="${ov.order.id}">
                <select name="status">
                    <option value="CONFIRMED">CONFIRMED</option>
                    <option value="SHIPPED">SHIPPED</option>
                    <option value="DELIVERED">DELIVERED</option>
                    <option value="CANCELLED">CANCELLED</option>
                </select>
                <button type="submit">Update Status</button>
            </form>
        </div>
    </c:forEach>
</body>
</html>