<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Login"/>
</jsp:include>
<div class="box-header">
    <h1 class="box-header__title">Authorization</h1>
    <div class="form">
        <div class="form__wrapper">
            <c:choose>
                <c:when test="${empty listUsers}">
                    <h2>No Users Found, Please Create New User</h2>
                </c:when>
                <c:otherwise>
                    <sf:form method="POST" commandName="userLogin" action="authorization">
                        <div class="form__input-group">
                            <label class="form__label" for="Names">Name:</label>
                            <input class="form__text" type="text" list="usersList" id="name" name="name" autocomplete="off"/>
                            <datalist id="usersList">
                                <c:forEach items="${listUsers}" var="user">
                                    <option>${user.name}</option>
                                </c:forEach>
                            </datalist>
                        </div>
                        <jsp:include page='../includes/user-password.jsp'/> 
                        <footer> 
                            <input class="form__submit" type="submit" value="Login"> 
                        </footer>
                    </sf:form>
                </c:otherwise>
            </c:choose>
            <a class="button button_wide" href="<c:url value='/authorization/createUser'/>">Create New User</a>
        </div>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>