<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Add User"/>
</jsp:include>
<div class="box-header">
    <h1 class="box-header__title">Add New User</h1>
    <div class="form">
        <div class="form__wrapper">
            <sf:form method="post" commandName="newUser" action="users" >
                <jsp:include page='../includes/user-name-password.jsp'/> 
                <div class="input-group">
                    <sf:label class="form__label" path="admin">Administrator:</sf:label>
                    <sf:checkbox class="form__checkbox" path="admin" value="true"/>
                </div>
                <input class="form__submit" type="submit" value="Save" />
            </sf:form>
            <a class="button button_back" href="/HomeFinance/users">Go Back</a>
        </div>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>    