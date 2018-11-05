<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE">
<html>
    <head>
        <title>${initParam['Title']} (Users)</title>
        <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
    </head>
    <body>
        <h1>List of Users</h1>
        <a href="users?new">Add New User</a>
        <table cellspacing="5" class="main-table">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Password</th>
                <th>Administrator</th>
                <th>Details</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="#{users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.password}</td>
                    <c:if test="${user.admin}">
                        <td>Yes</td>
                    </c:if>
                    <c:if test="${!user.admin}">
                        <td>No</td>
                    </c:if>
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
        <a href="/HomeFinance" class="go-back">Go back</a>
    </body>
</html>