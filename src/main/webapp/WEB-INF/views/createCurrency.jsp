<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE">
<html>
    <head>
        <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/centerHorizontal.css"/>" rel="stylesheet">
<!--        <link href="<c:url value="/resources/css/centerVertical.css"/>" rel="stylesheet">-->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${initParam['Title']} (Currencies)</title>
    </head>
    <body>
        
<!--        <h1>Currencies</h1>-->
        <div class="main">
            <div>Current user: ${sessionScope.user.name}</div>
            <h1>Create New Currency</h1>
            <form:form method="POST" commandName="currency" action="saveCurrency" class="form" 
                       modelAttribute="currency">
                <div class="fieldset">
                    <div class="input-group">
                        <form:label path="name">Name:</form:label>
                        <form:input path="name" />
                        <div><form:errors path="name" class="redColor" /></div>
                    </div>        
                </div>
                <footer class="footerButton"> 
                    <input type="submit" class="submit" value="Create" tabindex="4"> 
                </footer>
            </form:form>
            <c:if test="${!empty listCurrency}">
                <h3>Currency List</h3>
                <table class="tg">
                    <tr>
                        <th width="80">Currency ID</th>
                        <th width="120">Currency Name</th>
                        <th width="60">Edit</th>
                        <th width="60">Delete</th>
                    </tr>
                    <c:forEach items="${listCurrency}" var="currency">
                        <tr>
                            <td>${currency.id}</td>
                            <td>${currency.name}</td>
                            <td><a href="<c:url value='/edit/${currency.id}' />" >Edit</a></td>
                            <td><a href="<c:url value='/remove/${currency.id}' />" >Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
    </body>
</html>
