<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Create User"/>
</jsp:include>
<div class="center-box">
    <h1>Create New User</h1>
    <div class="form">
        <sf:form method="POST" commandName="user" action="saveUser" modelAttribute="user">
            <div class="input-group">
                <sf:label path="name">Name:</sf:label>
                <sf:input class="input-box" path="name"/>
                <div>
                    <sf:errors path="name" class="validation-error"/>
                </div>
            </div>        
            <div class="input-group">
                <sf:label path="password">Password:</sf:label>
                <sf:password class="input-box" path="password"/>
                <div>
                    <sf:errors class="validation-error" path="password"/>
                </div>
            </div> 
            <input class="button-submit" type="submit" value="Create"> 
        </sf:form>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>