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
        <div class="main">
            <a href="<c:url value='/createUser' />" class="createUser">Create User</a>
            <form:form method="POST" commandName="user" action="check-user" class="form">
                <fieldset class="fieldset">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${nameEmpty}">
                                <form:label path="name" cssClass="redColor">Name is mondatory!</form:label>
                            </c:when>    
                            <c:otherwise>
                                <form:label path="name">Name:</form:label>
                            </c:otherwise>
                        </c:choose>
                        <form:input path="name" />
                    </div>        
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${passwordEmpty}">
                                <form:label path="password" cssClass="redColor">Password is mondatory!</form:label>
                            </c:when>    
                            <c:otherwise>
                                <form:label path="password">Password:</form:label>
                            </c:otherwise>
                        </c:choose>
                        <form:password path="password"/>
                    </div> 
                </fieldset>

                <footer> 
                    <input type="submit" class="submit" value="Login" tabindex="4"> 
                </footer>

            </form:form>
            <h3>Users List</h3>
            <c:if test="${!empty listUsers}">
                <table class="tg">
                    <tr>
                        <th width="80">User ID</th>
                        <th width="120">User Name</th>
                        <th width="120">User Password</th>
                        <th width="60">Edit</th>
                        <th width="60">Delete</th>
                    </tr>
                    <c:forEach items="${listUsers}" var="user">
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