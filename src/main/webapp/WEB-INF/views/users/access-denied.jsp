<%@ page isErrorPage="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Access Denied!"/>
</jsp:include>
<div class="box-header__title">
    <h1 class="box-header__title">Access Denied!</h1>
    <div class="form error">
        <h2>User: "${user.name}" has no rights to access that resource.</h2>
    </div>
    <a class="button" href="/HomeFinance">Back to main page.</a>
</div>
<jsp:include page='../includes/footer.jsp'/>