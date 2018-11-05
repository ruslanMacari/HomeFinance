<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Access Denied!"/>
</jsp:include>
<div class="center-box">
    <div class="validation-error">
        <h1>Access denied!</h1>
        <h2>User "${user.name}" has no rights to access that resource.</h2>
    </div>
    <div>
        <a class="center-button" href="/HomeFinance">Back to main page.</a>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>