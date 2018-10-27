<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
    <title>${initParam['Title']} (Users)</title>
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
</head>
<body>
    <h1>List of users</h1>
    <a href="users?new">Add new user</a>
    <table cellspacing="5" class="main-table">
        <tr>
            <th>Name</th>
            <th>Password</th>
            <th>Details</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="#{users}" var="user">
            <tr>
                <td>${user.name}</td>
                <td>${user.password}</td>
                <td>
                    <a href="users/${user.id}">Go to page</a>
                </td>
                <td>
                    <sf:form action="users/${user.id}" method="delete" cssClass="delete">
                        <input type="submit" class="delete-button" value="" />
                    </sf:form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br />
    <a href="/">Go back</a>
</body>
</html>