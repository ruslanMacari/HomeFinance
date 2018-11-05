<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE">
<html>
    <head>
        <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${initParam['Title']} (Create User)</title>
    </head>
    <body>
        <div class="center-box">
            <h1>Add New User</h1>
            <form:form method="POST" commandName="user" action="saveUser" class="form" 
                       modelAttribute="user">
                <div class="fieldset">
                    <div class="input-group">
                        <form:label path="name">Name:</form:label>
                        <form:input cssClass="input-box" path="name"/>
                        <div><form:errors path="name" class="redColor" /></div>
                    </div>        
                    <div class="input-group">
                        <form:label path="password">Password:</form:label>
                        <form:password cssClass="input-box" path="password"/>
                        <div><form:errors path="password" class="redColor" /></div>
                    </div> 
                </div>
                <input type="submit" class="btnSubmit" value="Create" tabindex="4"> 
            </form:form>
        </div>
    </body>
</html>