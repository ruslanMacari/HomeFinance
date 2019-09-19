<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="form__input-group">
    <sf:label class="form__label" path="name"><spring:message code="user.name"/>:</sf:label>
    <sf:input class="form__text" path="name"/>
    <div>
        <sf:errors class="form__error" path="name"/>
    </div>
</div>        
<jsp:include page='../includes/user-password.jsp'/>