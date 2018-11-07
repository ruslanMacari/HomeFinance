<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Create User"/>
</jsp:include>
<div class="box-header">
    <h1 class="box-header__title">Create New User</h1>
    <div class="form">
        <div class="form__wrapper">
            <sf:form method="post" commandName="user" action="saveUser">
                <jsp:include page='../includes/user-name-password.jsp'/> 
                <input class="form__submit" type="submit" value="Create"> 
            </sf:form>
        </div>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>