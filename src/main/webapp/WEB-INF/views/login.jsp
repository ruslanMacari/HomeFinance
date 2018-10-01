<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${initParam['Title']} (Login)</title>
    </head>
    
    <body>
        <div>
            <h1>Authorization</h1>
            <c:choose>
                <c:when test="${empty listUsers}">
                    <h2>No Users Found, Please Create New User</h2>
                </c:when>
                <c:otherwise>
                    <form:form method="POST" commandName="user" action="home" class="form">
                        <div class="fieldset">
                            <div class="input-group">
                                <label for="Names">Name:</label>
                                <form:select path="id">
                                    <c:forEach items="${listUsers}" var="user">
                                        <form:option value="${user.id}" label="${user.name}"/>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <div class="input-group">
                                <form:label path="password">Password:</form:label>
                                <form:password path="password"/>
                                <div><form:errors path="password" class="redColor" /></div>
                            </div>    
                        </div>
                        <footer> 
                            <input type="submit" class="submit" value="Login" tabindex="4"> 
                        </footer>
                    </form:form>
                </c:otherwise>
            </c:choose>
                    <div class="createUserRef">
                <a href="<c:url value='/createUser' />" class="createUser">Create New User</a>
            </div>
            <c:if test="${!empty listUsersLimited}">
                <h3>Users List</h3>
                <table class="tg">
                    <tr>
                        <th width="80">User ID</th>
                        <th width="120">User Name</th>
                        <th width="120">User Password</th>
                        <th width="60">Edit</th>
                        <th width="60">Delete</th>
                    </tr>
                    <c:forEach items="${listUsersLimited}" var="user">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.password}</td>
                            <td><a href="<c:url value='/edit/${user.id}' />" >Edit</a></td>
                            <td><a href="<c:url value='/remove/${user.id}' />" >Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
</body>
</html>