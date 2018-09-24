<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
    <head>
        <title>Person Page</title>
        <style type="text/css">
            .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
            .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
            .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
            .tg .tg-4eph{background-color:#f9f9f9}
            .form .input-group {display:block; margin-bottom: 5px}
            .form .input-group label {margin-right: 0; width: 75px; display: inline-block}
            .form .submit {margin-top: 10px}
        </style>
    </head>
    <body>
        <h1>
            Add a Person
        </h1>

        <c:url var="addAction" value="/person/add" ></c:url>

        <form:form action="${addAction}" commandName="person" class="form">
            <c:if test="${!empty person.name}">
                <div class="input-group">
                    <form:label path="id">
                        <spring:message text="ID"/>
                    </form:label>
                    <form:input path="id" readonly="true" size="8"  disabled="true" />
                    <form:hidden path="id" />
                </div>
            </c:if>
            <div class="input-group">
                <!--        <label for="name">Name</label>
                        <input type="text" name="name" id="name" value=""/>-->
                <form:label path="name">
                    <spring:message text="Name"/>
                </form:label>
                <form:input path="name" />
            </div>
            <div class="input-group">
                <form:label path="country">
                    <spring:message text="Country"/>
                </form:label>
                <form:input path="country" />
            </div>
            <c:if test="${!empty person.name}">
                <input type="submit"
                       value="<spring:message text="Edit Person"/>"
                       class="submit"/>
            </c:if>
            <c:if test="${empty person.name}">
                <input type="submit"
                       value="<spring:message text="Add Person"/>"
                       class="submit"/>
            </c:if>	
        </form:form>
        <h3>Persons List</h3>
        <c:if test="${!empty listPersons}">
            <table class="tg">
                <tr>
                    <th width="80">Person ID</th>
                    <th width="120">Person Name</th>
                    <th width="120">Person Country</th>
                    <th width="60">Edit</th>
                    <th width="60">Delete</th>
                </tr>
                <c:forEach items="${listPersons}" var="person">
                    <tr>
                        <td>${person.id}</td>
                        <td>${person.name}</td>
                        <td>${person.country}</td>
                        <td><a href="<c:url value='/edit/${person.id}' />" >Edit</a></td>
                        <td><a href="<c:url value='/remove/${person.id}' />" >Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>
