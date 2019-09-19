<%@ page isErrorPage="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE>
<jsp:include page='includes/head.jsp'>
    <jsp:param name="title" value="users.details"/>
</jsp:include>
<div class="box-header__title">
    <h1 class="box-header__title"><spring:message code="exception-title"/></h1>
    <c:if test="${errorMessage != null}">
        <h2>${errorMessage}</h2>
    </c:if>
    <c:url var="home" value="/"/>
    <a class="button" href="${home}"><spring:message code="back-to-main"/></a>
</div>
<jsp:include page='includes/footer.jsp'/>