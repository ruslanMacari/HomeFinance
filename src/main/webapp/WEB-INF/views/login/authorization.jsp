<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<jsp:include page='../includes/head.jsp'>
    <jsp:param name="title" value="Login"/>
</jsp:include>
<div class="center-box">
    <h1>Authorization</h1>
    <div class="form">
        <c:choose>
            <c:when test="${empty listUsers}">
                <h2>No Users Found, Please Create New User</h2>
            </c:when>
            <c:otherwise>
                <sf:form method="POST" commandName="user" action="authorization">
                    <div class="input-group">
                        <label for="Names">Name:</label>
                        <input class="input-box" type="text" list="usersList" id="name" name="name" autocomplete="off"/>
                        <datalist id="usersList">
                            <c:forEach items="${listUsers}" var="user">
                                <option>${user.name}</option>
                            </c:forEach>
                        </datalist>
                    </div>
                    <div class="input-group">
                        <sf:label path="password">Password:</sf:label>
                        <sf:password class="input-box" path="password"/>
                        <div>
                            <sf:errors class="validation-error" path="password"/>
                        </div>
                    </div>
                    <footer> 
                        <input class="button-submit" type="submit" value="Login"> 
                    </footer>
                </sf:form>
            </c:otherwise>
        </c:choose>
        <a class="center-button" href="<c:url value='/authorization/createUser'/>">Create New User</a>
    </div>
</div>
<jsp:include page='../includes/footer.jsp'/>