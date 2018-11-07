<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<div class="form__input-group">
    <sf:label class="form__label" path="name">Name:</sf:label>
    <sf:input class="form__text" path="name"/>
    <div>
        <sf:errors class="form__error" path="name"/>
    </div>
</div>        
<jsp:include page='../includes/user-password.jsp'/>