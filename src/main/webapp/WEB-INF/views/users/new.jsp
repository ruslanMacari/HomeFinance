<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Add User"/>
</jsp:include>
<div class="box-header">
    <h1 class="box-header__title">Add New User</h1>
    <div class="form">
        <div class="form__wrapper">
            <sf:form method="post" action="users" commandName="newUser">
                <div class="form__input-group">
                    <label class="form__label" for="name">Name:</label>
                    <input class="form__text" name="name" id="name" value="${newUser.name}"/>
                    <div>
                        <sf:errors class="form__error" path="name"/>
                    </div>
                </div>
                <div class="form__input-group">
                    <label class="form__label" for="password">Password</label>
                    <input class="form__text" name="password" id="password" value="${newUser.password}" type="password"/>
                    <div>
                        <sf:errors class="form__error" path="password"/>
                    </div>
                </div>
                <div class="input-group">
                    <label class="form__label" for="admin">Administrator:</label>
                    <input class="form__checkbox" id="admin" name="admin" value="true" type="checkbox"/>
                </div>
                <input class="form__submit" type="submit" value="Save" />
            </sf:form>
            <a class="button button_back" href="/HomeFinance/users">Go Back</a>
        </div>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>    