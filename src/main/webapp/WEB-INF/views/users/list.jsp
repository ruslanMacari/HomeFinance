<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Users"/>
</jsp:include>
<h1 class="box-header__title">List of Users</h1>
<a class="button" href="users?new">Add New User</a>
<div class="table">
    <div class="table__heading">
        <div class="table__row">
            <div class="table__cell table__cell_head">ID</div>
            <div class="table__cell table__cell_head">Name</div>
            <div class="table__cell table__cell_head">Password</div>
            <div class="table__cell table__cell_head">Administrator</div>
            <div class="table__cell table__cell_head">Details</div>
            <div class="table__cell table__cell_head">Delete</div>
        </div>
    </div>
    <div class="table__body">
        <c:forEach items="#{users}" var="user">
            <div class="table__row">
                <div class="table__cell">${user.id}</div>
                <div class="table__cell">${user.name}</div>
                <div class="table__cell">${user.password}</div>
                <c:if test="${user.admin}">
                    <div class="table__cell">Yes</div>
                </c:if>
                <c:if test="${!user.admin}">
                    <div class="table__cell">No</div>
                </c:if>
                <div class="table__cell">
                    <a class="button" href="users/${user.id}">Details</a>
                </div>
                <div class="table__cell">
                    <sf:form class="form" action="users/${user.id}" method="delete">
                        <input class="button button_delete" type="submit" value="Delete"/>
                    </sf:form>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<a class="button button_back" href="/HomeFinance">Go back</a>
<jsp:include page='../includes/footer.jsp'/>