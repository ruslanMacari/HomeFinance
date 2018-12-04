<%@ page isErrorPage="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE>
<jsp:include page='includes/head.jsp'>
    <jsp:param name="title" value="resource-not-found"/>
</jsp:include>
<div class="box-header__title">
    <h1 class="box-header__title"><spring:message code="resource-not-found"/></h1>
    <a class="button" href="/HomeFinance"><spring:message code="back-to-main"/></a>
</div>
<jsp:include page='includes/footer.jsp'/>