<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="users.new"/>
</jsp:include>
<h1 class="box-header__title"><spring:message code="users.new"/></h1>
<div class="form">
    <div class="form__wrapper">
        <c:url value="/users/new" var="usersNew"/>
        <sf:form method="post" commandName="newUser" action="${usersNew}" >
            <jsp:include page='../includes/user-name-password.jsp'/> 
            <div class="input-group">
                <label class="form__label" for="admin">
                    <spring:message code="admin"/>
                </label>
                <input class="form__checkbox" id="admin" name="admin" type="checkbox" /> 
            </div>
            <input class="form__submit" type="submit" value="<spring:message code="save"/>" />
        </sf:form>
        <a class="button button_back" href="/HomeFinance/users"><spring:message code="back"/></a>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>    