<%@ page isErrorPage="true" %>
<!DOCTYPE">
<html>
    <head>
        <title>${initParam['Title']} (Access Denied!)</title>
        <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet">
    </head>
    <body>
        <p>Access denied! User "${user.name}" has no rights to access that resource.</p>
        <div><a href="/HomeFinance" class="go-back">Back to main page.</a></div>
    </body>
</html>