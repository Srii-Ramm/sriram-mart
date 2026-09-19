<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head><title><c:out value="${product.name}"/> - SriramMart</title></head>
<body>
    <p><a href="${pageContext.request.contextPath}/products">Back to products</a></p>

    <h1><c:out value="${product.name}"/></h1>
    <c:if test="${not empty product.imageUrl}">
        <img src="${product.imageUrl}" alt="product image" width="200">
    </c:if>
    <p><c:out value="${product.description}"/></p>
    <p>Category: <c:out value="${product.category}"/></p>
    <p>Price: <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₹"/></p>
    <p>Stock: <c:out value="${product.quantity}"/></p>
    <p>Average Rating: <fmt:formatNumber value="${avgRating}" maxFractionDigits="1"/> / 5</p>

    <c:if test="${sessionScope.userRole == 'BUYER'}">
        <form method="get" action="${pageContext.request.contextPath}/cart/add">
        </form>
        <form method="post" action="${pageContext.request.contextPath}/cart/add">
            <input type="hidden" name="productId" value="${product.id}">
            <input type="number" name="quantity" value="1" min="1">
            <button type="submit">Add to Cart</button>
        </form>

        <c:if test="${param.reviewError == '1'}">
            <p style="color:red;">You can only review products you have purchased, with a rating 1-5.</p>
        </c:if>

        <h3>Leave a Review</h3>
        <form method="post" action="${pageContext.request.contextPath}/reviews/add">
            <input type="hidden" name="productId" value="${product.id}">
            <select name="rating">
                <option value="5">5</option><option value="4">4</option><option value="3">3</option>
                <option value="2">2</option><option value="1">1</option>
            </select>
            <textarea name="comment" placeholder="Your review"></textarea><br>
            <button type="submit">Submit Review</button>
        </form>
    </c:if>

    <h3>Reviews</h3>
    <c:forEach var="r" items="${reviews}">
        <p><b><c:out value="${r.rating}"/>/5</b> - <c:out value="${r.comment}"/></p>
    </c:forEach>
</body>
</html>