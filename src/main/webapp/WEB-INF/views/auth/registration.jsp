<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Create User"/>
</jsp:include>
<h1 class="box-header__title"><spring:message code="login.registration"/></h1>
<div class="form">
    <div class="form__wrapper">
        <sf:form method="post" commandName="user" action="registration">
            <jsp:include page='../includes/user-name-password.jsp'/> 
            <input class="form__submit" type="submit" value="<spring:message code="login.register"/>"> 
        </sf:form>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>