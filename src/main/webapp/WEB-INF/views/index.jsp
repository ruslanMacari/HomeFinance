<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE">
<html>
    <head>
        <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${initParam['Title']} (Home)</title>
    </head>
    <body>
        <div>
            <a href="<c:url value='/authorization/logout'/>">Log Out</a>
        </div>
        <c:if test="${sessionScope.user.admin}">
            <div>
                <a href="<c:url value='/users'/>">Users</a>
            </div>    
        </c:if>
        <h1>Hello ${sessionScope.user.name}!</h1>
        <div class="createUserRef">
            <a href="<c:url value='/createCurrency' />" class="createCurrency">Create New Currency</a>
        </div>
    </body>
</html>
