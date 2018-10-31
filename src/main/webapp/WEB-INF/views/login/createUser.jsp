<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE">
<html>
    <head>
        <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/centerHorizontal.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/centerVertical.css"/>" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${initParam['Title']} (Create User)</title>
    </head>
    <body>
        <div class="main">
            <h1>Create New User</h1>
            <form:form method="POST" commandName="user" action="saveUser" class="form" 
                       modelAttribute="user">
                <div class="fieldset">
                    <div class="input-group">
                        <form:label path="name">Name:</form:label>
                        <form:input path="name" />
                        <div><form:errors path="name" class="redColor" /></div>
                    </div>        
                    <div class="input-group">
                        <form:label path="password">Password:</form:label>
                        <form:password path="password"/>
                        <div><form:errors path="password" class="redColor" /></div>
                    </div> 
                </div>
                <footer class="footerButton"> 
                    <input type="submit" class="submit" value="Create" tabindex="4"> 
                </footer>
            </form:form>
        </div>
    </body>
</html>