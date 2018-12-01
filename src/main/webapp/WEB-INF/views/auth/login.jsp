<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="login"/>
</jsp:include>
<h1 class="box-header__title">Home Finance</h1>
<div class="form">
    <div class="form__wrapper">
        <c:url value="/login" var="loginUrl" />
        <sf:form method="POST" commandName="user" action="${loginUrl}">
            <div class="form__input-group">
                <label class="form__label" for="name"><spring:message code="user.name"/>:</label>
                <input class="form__text" type="text" list="usersList" 
                       id="name" name="username" autocomplete="off"/>
                <datalist id="usersList">
                    <c:forEach items="${listUsers}" var="user">
                        <option>${user.name}</option>
                    </c:forEach>
                </datalist>
            </div>
                <div class="form__input-group">
                    <sf:label class="form__label" path="password"><spring:message code="user.password"/>:</sf:label>
                    <sf:password class="form__text" path="password"/>
                    <c:if test="${param.error != null}">        
                        <div class="form__error">
                            <spring:message code="login.error"/>
                        </div>
                    </c:if>
                </div>
            <div class="input-group">
                <label class="form__label" for="remember-me">
                    <spring:message code="login.remember-me"/>
                </label>
                <input class="form__checkbox" id="remember-me" name="remember-me" type="checkbox" /> 
            </div>        
            <footer> 
                <input class="form__submit" type="submit" value="<spring:message code="login"/>"> 
            </footer>
        </sf:form>
        <a class="button button_wide" href="<c:url value='/login/registration'/>"><spring:message code="login.registration"/></a>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>