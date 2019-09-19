<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="users"/>
</jsp:include>
<h1 class="box-header__title"><spring:message code="users.header"/></h1>
<a class="button" href="users/new"><spring:message code="users.new"/></a>
<div class="table">
    <div class="table__heading">
        <div class="table__row">
            <div class="table__cell table__cell_head">#</div>
            <div class="table__cell table__cell_head">ID</div>
            <div class="table__cell table__cell_head"><spring:message code="user.name"/></div>
            <div class="table__cell table__cell_head"><spring:message code="admin"/></div>
            <div class="table__cell table__cell_head"><spring:message code="details"/></div>
            <div class="table__cell table__cell_head"><spring:message code="delete"/></div>
        </div>
    </div>
    <div class="table__body">
        <c:forEach items="#{users}" var="user" varStatus="count">
            <div class="table__row">
                <div class="table__cell">${count.count}</div>
                <div class="table__cell">${user.id}</div>
                <div class="table__cell">${user.name}</div>
                <c:set var = "admin" value = "${user.hasAdmin()}"/>
                <c:if test="${admin}">
                    <div class="table__cell"><spring:message code="yes"/></div>
                </c:if>
                <c:if test="${!admin}">
                    <div class="table__cell"><spring:message code="no"/></div>
                </c:if>
                <div class="table__cell">
                    <a class="button" href="users/${user.id}"><spring:message code="details"/></a>
                </div>
                <div class="table__cell">
                    <sf:form class="form" action="users/${user.id}" method="delete">
                        <input class="button button_delete" type="submit" value="<spring:message code="delete"/>"/>
                    </sf:form>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<c:url var="home" value="/"/>
<a class="button button_back" href="${home}"><spring:message code="back"/></a>
<jsp:include page='../includes/footer.jsp'/>