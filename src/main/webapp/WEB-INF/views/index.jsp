<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE>
<jsp:include page="includes/head.jsp">
    <jsp:param name="title" value="home"/>
</jsp:include>
<div class="box-header__title">
    <sec:authentication var="principal" property="principal" />
    <h1><spring:message code="hello"/> ${principal.username}!</h1>
    <c:url var="logoutUrl" value="/logout"/>
    <sf:form action="${logoutUrl}" method="post">
        <input class="button" type="submit" value="<spring:message code="logout"/>" />
    </sf:form>
    <sec:authorize access="hasAuthority(@role.ADMIN) and isAuthenticated()">
        <a class="button" href="<c:url value='/users'/>"><spring:message code="users"/></a>
    </sec:authorize>
    <a class="button" href="<c:url value='/currencies'/>"><spring:message code="currencies"/></a>
</div>
<jsp:include page='includes/footer.jsp'/>
