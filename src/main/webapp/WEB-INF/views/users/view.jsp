<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="User page"/>
</jsp:include>
<div class="box-header">
    <h1 class="box-header__title">User info</h1>
    <div id="form">
        <div class="form__wrapper">
            <sf:form method="post" modelAttribute="user">
                <div class="form__input-group">
                    <label>ID: ${user.id}</label>
                </div>
                <div class="form__input-group">
                    <sf:label class="form__label" path="name">Name:</sf:label>
                    <sf:input class="form__text" path="name" id="name" value="${user.name}" disabled="true"/>
                    <div>
                        <sf:errors class="form__error" path="name"/>
                    </div>
                </div>
                <div class="form__input-group">
                    <sf:label cssClass="form__lable" path="password">Password:</sf:label>
                    <sf:input cssClass="form__text" path="password" id="password" value="${user.password}" disabled="true" />
                    <div>
                        <sf:errors class="form__error" path="password"/>
                    </div>
                </div>
                <div class="form__input-group">       
                    <label class="form__label" for="admin">Administrator:</label>
                    <c:if test="${user.admin}">
                        <input class="form__checkbox" id="admin" name="admin" value="true" type="checkbox" disabled="true" checked/>
                    </c:if>
                    <c:if test="${!user.admin}">
                        <input class="form__checkbox" id="admin" name="admin" value="true" type="checkbox" disabled="true"/>
                    </c:if>
                </div>
                <input class="button" type="button" value="Unlock" id="unlock" />
                <input class="form__submit hidden" type="submit" value="Save" id="save"/>
            </sf:form>
            <a class="button button_back" href="/HomeFinance/users" class="go-back">Go Back</a>
        </div>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>