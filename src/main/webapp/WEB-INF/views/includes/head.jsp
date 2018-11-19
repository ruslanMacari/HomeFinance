<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>${initParam['Title']}: ${param.title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
<link rel="icon" href="<c:url value="/resources/img/home-finance-icon.png"/>" type="image/vnd.microsoft.icon">
<div class="box-header">
<div class="text-align-right"> 
    <a class="button-language" href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">
        <img class="button-language__image" src="<c:url value="/resources/img/flag-en.png"/>">
    </a>
    <a class="button-language" href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru">
        <img class="button-language__image" src="<c:url value="/resources/img/flag-ru.png"/>">
    </a>
</div>