<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${initParam['Title']} (Home)</title>
    </head>
    <body>
        <div>
            <a href="<c:url value='/login/logout'/>">Log Out</a>
        </div>
        <h1>Hello ${sessionScope.user.name}!</h1>
        <div class="createUserRef">
            <a href="<c:url value='/createCurrency' />" class="createCurrency">Create New Currency</a>
        </div>
    </body>
</html>