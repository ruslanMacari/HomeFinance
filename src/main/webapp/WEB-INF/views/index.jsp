<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<jsp:include page="includes/head.jsp">
    <jsp:param name="title" value="Home"/>
</jsp:include>
<div class="box-header__title">
    <h1>Hello ${sessionScope.user.name}!</h1>

<!--<a class="button" href="${logoutUrl}">Log Out</a>-->
    <c:url var="logoutUrl" value="/logout"/>
    <sf:form action="${logoutUrl}" method="post">
        <input class="button" type="submit" value="Log out" />
    </sf:form>
    <c:if test="${sessionScope.user.admin}">
        <a class="button" href="<c:url value='/users'/>">Users</a>
    </c:if>
    <a class="button" href="<c:url value='/createCurrency'/>">Create New Currency</a>
</div>
<jsp:include page='includes/footer.jsp'/>
