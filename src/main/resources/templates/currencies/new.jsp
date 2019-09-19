<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="currencies.new"/>
</jsp:include>
<h1 class="box-header__title"><spring:message code="currencies.new"/></h1>
<div class="form">
    <div class="form__wrapper">
        <c:url value="/currencies/new" var="currenciesNew"/>
        <sf:form method="post" commandName="newCurrency" action="${currenciesNew}" >
            <sf:label class="form__label" path="code"><spring:message code="code"/>:</sf:label>
            <sf:input class="form__text" path="code"/>
            <div>
                <sf:errors class="form__error" path="code"/>
            </div>
            <sf:label class="form__label" path="name"><spring:message code="description"/>:</sf:label>
            <sf:input class="form__text" path="name"/>
            <div>
                <sf:errors class="form__error" path="name"/>
            </div>
            <input class="form__submit" type="submit" value="<spring:message code="save"/>" />
        </sf:form>
        <c:url var="currencies" value="/currencies"/>
        <a class="button button_back" href="${currencies}"><spring:message code="back"/></a>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>    