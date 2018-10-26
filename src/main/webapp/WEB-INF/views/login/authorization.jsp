<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE>
<html>
    <head>
        <link href="<c:url value="/resources/css/common.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/centerHorizontal.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/centerVertical.css"/>" rel="stylesheet">
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
                    <form:form method="POST" commandName="user" action="authorization" class="form">
                        <div class="fieldset">
                            <div class="input-group">
                                <label for="Names">Name:</label>
                                <input type="text" list="usersList" id="name" name="name" autocomplete="off"/>
                                <datalist id="usersList">
                                    <c:forEach items="${listUsers}" var="user">
                                        <option>${user.name}</option>
                                    </c:forEach>
                                </datalist>
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
                <a href="<c:url value='/login/createUser' />" class="createUser">Create New User</a>
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