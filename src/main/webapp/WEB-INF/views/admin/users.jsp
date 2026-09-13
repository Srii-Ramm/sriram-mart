<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Users - Admin</title></head>
<body>
    <h1>All Users</h1>
    <table border="1" cellpadding="6">
        <tr><th>ID</th><th>Name</th><th>Email</th><th>Role</th></tr>
        <c:forEach var="u" items="${users}">
            <tr>
                <td><c:out value="${u.id}"/></td>
                <td><c:out value="${u.name}"/></td>
                <td><c:out value="${u.email}"/></td>
                <td><c:out value="${u.role}"/></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>