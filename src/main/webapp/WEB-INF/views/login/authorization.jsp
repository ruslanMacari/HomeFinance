<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Login"/>
</jsp:include>
<h1 class="box-header__title"><spring:message code="authorization"/></h1>
<div class="form">
    <div class="form__wrapper">
        <c:url value="/authorization" var="authorizationUrl" />
        <sf:form method="POST" commandName="userLogin" action="${authorizationUrl}">
            <div class="form__input-group">
                <label class="form__label" for="Names"><spring:message code="user.name"/>:</label>
                <input class="form__text" type="text" list="usersList" id="name" name="name" autocomplete="off"/>
                <datalist id="usersList">
                    <c:forEach items="${listUsers}" var="user">
                        <option>${user.name}</option>
                    </c:forEach>
                </datalist>
            </div>
            <jsp:include page='../includes/user-password.jsp'/> 
            <c:if test="${param.error != null}">        
                Failed to login.
                <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                    Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
                </c:if>
            </c:if>
            <footer> 
                <input class="form__submit" type="submit" value="<spring:message code="login"/>"> 
            </footer>
            <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
        </sf:form>
        <a class="button button_wide" href="<c:url value='/authorization/createUser'/>"><spring:message code="create-new-user"/></a>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>