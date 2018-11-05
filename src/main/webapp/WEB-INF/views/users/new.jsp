<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Add User"/>
</jsp:include>
<div class="center-box">
    <h1>Add New User</h1>
    <div class="form">
        <sf:form method="post" action="users">
            <div class="input-group">
                <label for="name">Name:</label>
                <input class="input-box" name="name" id="name" value="${user.name}"/>
            </div>
            <div class="input-group">
                <label for="password">Password</label>
                <input class="input-box" name="password" id="password" value="${user.password}" type="password"/>
            </div>
            <div class="input-group">
                <label class="checkbox" for="admin">Administrator:</label>
                <input class="checkbox" id="admin" name="admin" value="true" type="checkbox"/>
            </div>
            <input class="button-submit" type="submit" value="Save" />
        </sf:form>
        <a class="center-button" href="/HomeFinance/users">Go Back</a>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>    