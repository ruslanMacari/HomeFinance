<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>${initParam['Title']} (Add User)</title>
        <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet">
    </head>
    <body>
        <h2>Add New User</h2>
        <div id="list">
            <sf:form method="post" action="users">
                <ul>
                    <li>
                        <label for="name">Name:</label>
                        <input name="name" id="name" value="${user.name}"/>
                    </li>
                    <li>
                        <label for="password">Department:</label>
                        <input name="password" id="password" value="${user.password}" />
                    </li>
                    <li>
                        <label for="admin">Administrator:</label>
                        <input id="admin" name="admin" value="true" type="checkbox"/>
                    </li>
                    <input type="submit" value="Save" id="save" />
                </ul>
            </sf:form>
        </div>
        <a href="/HomeFinance/users" class="go-back">Go Back</a>
    </body>
</html>