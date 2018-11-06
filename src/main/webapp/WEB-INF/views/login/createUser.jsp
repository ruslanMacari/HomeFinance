<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Create User"/>
</jsp:include>
<div class="box-header">
    <h1 class="box-header__title">Create New User</h1>
    <div class="form">
        <div class="form__wrapper">
            <sf:form method="POST" commandName="user" action="saveUser" modelAttribute="user">
                <div class="form__input-group">
                    <sf:label class="form__label" path="name">Name:</sf:label>
                    <sf:input class="form__text" path="name"/>
                    <div>
                        <sf:errors class="form__error" path="name"/>
                    </div>
                </div>        
                <div class="form__input-group">
                    <sf:label class="form__label" path="password">Password:</sf:label>
                    <sf:password class="form__text" path="password"/>
                    <div>
                        <sf:errors class="form__error" path="password"/>
                    </div>
                </div> 
                <input class="form__submit" type="submit" value="Create"> 
            </sf:form>
        </div>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>