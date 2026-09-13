<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head><title>My Cart - SriramMart</title></head>
<body>
    <p><a href="${pageContext.request.contextPath}/products">Continue shopping</a></p>
    <h1>My Cart</h1>

    <table border="1" cellpadding="6">
        <tr><th>Product</th><th>Qty</th><th>Line Total</th><th></th></tr>
        <c:forEach var="line" items="${lines}">
            <tr>
                <td><c:out value="${line.product.name}"/></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/cart/update" style="display:inline;">
                        <input type="hidden" name="productId" value="${line.product.id}">
                        <input type="number" name="quantity" value="${line.quantity}" min="1" style="width:60px;">
                        <button type="submit">Update</button>
                    </form>
                </td>
                <td><fmt:formatNumber value="${line.lineTotal}" type="currency" currencySymbol="₹"/></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/cart/remove" style="display:inline;">
                        <input type="hidden" name="productId" value="${line.product.id}">
                        <button type="submit">Remove</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h3>Total: <fmt:formatNumber value="${grandTotal}" type="currency" currencySymbol="₹"/></h3>

    <c:if test="${not empty lines}">
        <a href="${pageContext.request.contextPath}/checkout"><button>Proceed to Checkout</button></a>
    </c:if>
</body>
</html>