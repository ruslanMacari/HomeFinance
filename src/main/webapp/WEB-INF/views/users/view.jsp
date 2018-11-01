<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE">
<html>
    <head>
        <title>User page</title>
        <link rel="stylesheet" href="/HomeFinance/resources/css/style.css" type="text/css">
    </head>
    <body>
        <h2>User info</h2>
        <div id="list">
            <sf:form method="post" modelAttribute="user">
                <ul>
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
                        <input type="button" value="Unlock" id="unlock" />
                        <input type="submit" value="Save" id="save" class="hidden" />
                    </li>
                </ul>
            </sf:form>
        </div>
        <a href="/HomeFinance/users" class="space">Go Back</a>
        <script src="/HomeFinance/resources/js/main.js"></script>
    </body>
</html>