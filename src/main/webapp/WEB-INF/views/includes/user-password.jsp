<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="form__input-group">
    <sf:label class="form__label" path="password"><spring:message code="password"/>Password:</sf:label>
    <sf:password class="form__text" path="password"/>
    <div>
        <sf:errors class="form__error" path="password"/>
    </div>
</div>