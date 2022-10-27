<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
       <c:import url="${contextPath}/WEB-INF/view/navigation.jsp"/>
        <form action="/developer/developer_info">
            <label for="lastName"> Last name: </label><br>
            <input type="text" id="lastName" name="lastName"><br>
            <label for="firstName"> First name: </label><br>
            <input type="text" id="firstName" name="firstName"><br>
            <button type="submit">Find</button>
        </form>
        <c:choose>
          <c:when test="${isPresent}">
              <c:out value="Information about developer "/> <b><c:out value="${developer.lastName} ${developer.firstName} : "/></b><br>
             <c:out value="${developer.age} years old,"/><br>
             <c:out value="works in company ${developer.company.companyName}, with salary ${developer.salary},"/><br>
             <c:out value="participates in such projects : "/>
             <c:forEach var = "project" items="${projects}">
                     <c:out value = "${project}"/>
                 </c:forEach><br>
             <c:out value="has such skill set :"/>
                 <c:forEach var = "skill" items="${skills}">
                     <c:out value = "${skill}"/>
                 </c:forEach>
          </c:when>
          <c:otherwise>
             <c:out value="There is not developer by specified name. Please enter correct data."/>
          </c:otherwise>
        </c:choose>
    </body>
</html>