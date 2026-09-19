<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Checkout - SriramMart</title></head>
<body>
    <h1>Checkout</h1>
    <c:if test="${not empty error}"><p style="color:red;"><c:out value="${error}"/></p></c:if>

    <p>Total items: <c:out value="${cart.items.size()}"/></p>

    <form method="post" action="${pageContext.request.contextPath}/checkout">
        <button type="submit">Confirm Payment (Mock)</button>
    </form>
</body>
</html>