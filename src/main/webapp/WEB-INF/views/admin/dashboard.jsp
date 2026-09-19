<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Admin - SriramMart</title></head>
<body>
    <h1>Admin Dashboard</h1>
    <ul>
        <li><a href="${pageContext.request.contextPath}/admin/users">View Users</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/orders">View Orders</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/products">Moderate Products</a></li>
    </ul>
</body>
</html>