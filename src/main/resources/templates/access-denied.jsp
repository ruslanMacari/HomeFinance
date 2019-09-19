<%@ page isErrorPage="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE>
<jsp:include page='includes/head.jsp'>
    <jsp:param name="title" value="access-denied"/>
</jsp:include>
<div class="box-header__title">
    <h1 class="box-header__title"><spring:message code="access-denied"/></h1>
    <div class="form error">
        <h2><spring:message code="access-denied-message" arguments="${user.name}"/></h2>
    </div>
    <c:url var="home" value="/"/>
    <a class="button" href="${home}"><spring:message code="back-to-main"/></a>
</div>
<jsp:include page='includes/footer.jsp'/>