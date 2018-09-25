<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link href="<c:url value="/resources/css/home.css" />" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Java Home Finance(Create User)</title>
    </head>
    <body>
        <div class="main">
            <h2>Create New User</h2>
            <form:form method="POST" commandName="user" action="check-user" class="form" 
                       modelAttribute="user">
                <fieldset class="fieldset">
                    <div class="input-group">
                        <form:label path="name">Name:</form:label>
                        <form:input path="name" />
                        <form:errors path="name" class="error-message" />
                    </div>        
                    <div class="input-group">
                        <form:label path="password">Password:</form:label>
                        <form:password path="password"/>
                        <form:errors path="password" class="error-message" />
                    </div> 
                </fieldset>

                <footer class="footerButton"> 
                    <input type="submit" class="submit" value="Create" tabindex="4"> 
                </footer>

            </form:form>
        </div>
    </body>
</html>