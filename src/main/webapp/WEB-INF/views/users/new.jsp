<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>${initParam['Title']} (Add User)</title>
        <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
    </head>
    <body>
        <h1>Add New User</h1>
        <div class="center-box">
            <sf:form method="post" action="users">
                <div class="fieldset">
                    <div class="input-group">
                        <label for="name">Name:</label>
                        <input class="input-box" name="name" id="name" value="${user.name}"/>
                    </div>
                    <div class="input-group">
                        <label for="password">Department:</label>
                        <input class="input-box" name="password" id="password" value="${user.password}" />
                    </div>
                    <div class="input-group">
                        <label for="admin">Administrator:</label>
                        <input id="admin" name="admin" value="true" type="checkbox"/>
                    </div>
                    <input type="submit" value="Save" id="save" />
                </div>
            </sf:form>
        </div>
        <a href="/HomeFinance/users" class="go-back">Go Back</a>
    </body>
</html>