<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="users.details"/>
</jsp:include>
<h1 class="box-header__title"><spring:message code="users.details"/></h1>
<div id="form">
    <div class="form__wrapper">
        <sf:form method="post" modelAttribute="user">
            <div class="form__input-group">
                <sf:label class="form__label" path="name"><spring:message code="user.name"/>:</sf:label>
                <sf:input class="form__text" path="name" id="name" value="${user.name}" disabled="true"/>
                <div>
                    <sf:errors class="form__error" path="name"/>
                </div>
            </div>
            <div class="form__input-group">
                <div class="form__input-group">
                    <label class="form__label" for="changePassword"><spring:message code="change-password"/>:</label>
                    <input class="form__checkbox" id="changePassword" name="changePassword" value="true" type="checkbox" disabled="true"/>
                </div>
                <sf:password class="form__text" path="password" id="password" disabled="true"/>
                <div>
                    <sf:errors class="form__error" path="password"/>
                </div>
            </div>
            <div class="form__input-group">       
                <label class="form__label" for="admin"><spring:message code="admin"/>:</label>
                <c:set var = "admin" value = "${user.hasAdmin()}"/>
                <c:if test="${admin}">
                    <input class="form__checkbox" id="admin" name="admin" value="true" type="checkbox" disabled="true" checked/>
                </c:if>
                <c:if test="${!admin}">
                    <input class="form__checkbox" id="admin" name="admin" value="true" type="checkbox" disabled="true"/>
                </c:if>
            </div>
            <input class="button" type="button" value="<spring:message code="edit"/>" id="unlock" />
            <input class="form__submit hidden" type="submit" value="<spring:message code="save"/>" id="save"/>
        </sf:form>
        <c:url var="users" value="/users"/>    
        <a class="button button_back" href="${users}" class="go-back"><spring:message code="back"/></a>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>