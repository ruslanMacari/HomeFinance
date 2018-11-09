<%@ page isErrorPage="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<jsp:include page='includes/head.jsp'>
    <jsp:param name="title" value="Resource Not Found!"/>
</jsp:include>
<div class="box-header">
    <div class="box-header__title">
        <h1 class="box-header__title">Resource Not Found!</h1>
        <a class="button" href="/HomeFinance">Back to main page.</a>
    </div>
</div>
<jsp:include page='includes/footer.jsp'/>