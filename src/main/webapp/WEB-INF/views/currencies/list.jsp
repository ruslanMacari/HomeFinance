<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="currencies"/>
</jsp:include>
<h1 class="box-header__title"><spring:message code="currencies.header"/></h1>
<a class="button" href="currencies/new"><spring:message code="currencies.new"/></a>
<div class="table">
    <div class="table__heading">
        <div class="table__row">
            <div class="table__cell table__cell_head">#</div>
            <div class="table__cell table__cell_head">ID</div>
            <div class="table__cell table__cell_head"><spring:message code="description"/></div>
            <div class="table__cell table__cell_head"><spring:message code="code"/></div>
            <div class="table__cell table__cell_head"><spring:message code="details"/></div>
            <div class="table__cell table__cell_head"><spring:message code="delete"/></div>
        </div>
    </div>
    <div class="table__body">
        <c:forEach items="#{currencies}" var="currency" varStatus="count">
            <div class="table__row">
                <div class="table__cell">${count.count}</div>
                <div class="table__cell">${currency.id}</div>
                <div class="table__cell">${currency.name}</div>
                <div class="table__cell">${currency.code}</div>
                <c:url value="currencies/${currency.id}" var="currencyIdUrl" />
                <div class="table__cell">
                    <a class="button" href="${currencyIdUrl}"><spring:message code="details"/></a>
                </div>
                <div class="table__cell">
                    <sf:form class="form" action="${currencyIdUrl}" method="delete">
                        <input class="button button_delete" type="submit" value="<spring:message code="delete"/>"/>
                    </sf:form>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<a class="button button_back" href="/HomeFinance"><spring:message code="back"/></a>
<jsp:include page='../includes/footer.jsp'/>