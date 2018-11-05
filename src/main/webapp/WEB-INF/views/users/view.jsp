<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE">
<html>
    <head>
        <title>User page</title>
        <link rel="stylesheet" href="/HomeFinance/resources/css/main.css" type="text/css">
    </head>
    <body>
        <h2>User info</h2>
        <div id="list">
            <sf:form method="post" modelAttribute="user">
                <ul>
                    <li>
                        <label>ID: ${user.id}</label>
                    </li>
                    <li>
                        <sf:label path="name">Name:</sf:label>
                        <sf:input path="name" id="name" value="${user.name}" disabled="true"/>
                        <div><sf:errors path="name" class="error" /></div>
                    </li>
                    <li>
                        <sf:label path="password">Password:</sf:label>
                        <sf:input path="password" id="password"
                               value="${user.password}" disabled="true" />
                        <div><sf:errors path="password" class="error" /></div>
                    </li>
                    <li>
                        <label for="admin">Administrator:</label>
                        <c:if test="${user.admin}">
                            <input id="admin" name="admin" value="true" type="checkbox" disabled="true" checked/>
                        </c:if>
                        <c:if test="${!user.admin}">
                            <input id="admin" name="admin" value="true" type="checkbox" disabled="true"/>
                        </c:if>
                    </li>
                    <li>
                        <input type="button" value="Unlock" id="unlock" />
                        <input type="submit" value="Save" id="save" class="hidden" />
                    </li>
                </ul>
            </sf:form>
        </div>
        <a href="/HomeFinance/users" class="go-back">Go Back</a>
        <script type="module" src="/HomeFinance/resources/js/dist/main.js"></script>
    </body>
</html>