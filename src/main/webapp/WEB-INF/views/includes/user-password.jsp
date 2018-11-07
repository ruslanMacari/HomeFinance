<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<div class="form__input-group">
    <sf:label class="form__label" path="password">Password:</sf:label>
    <sf:password class="form__text" path="password"/>
    <div>
        <sf:errors class="form__error" path="password"/>
    </div>
</div>