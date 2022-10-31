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
            <form action="/developer/update" method="post">
                <p hidden>
                      <input id="lastName" name="lastName" value =${lastName}>
                      <input  id="firstName" name="firstName" value = ${firstName}>
                </p>
                <label for="age">Age:</label>
                <input type="age" id="age" name="age"><br>
                <p><b> Choose company where the developer works</b></p>
                 <c:forEach var = "company" items="${companies}">
                    <input type="radio" name="companyName" value="${company.companyName}">${company.companyName}</input><br/>
                  </c:forEach><br>
                 <label for="salary">His salary:</label>
                 <input type="salary" id="salary" name="salary"><br>
                 <p><b> Choose project the developer participate</b></p>
                 <c:forEach var = "project" items="${projects}">
                    <input type="checkbox" name="projectName" value="${project.projectName}">${project.projectName}</input><br/>
                 </c:forEach><br>
                 <label for="language">Language:</label>
                 <input type="text" id="language" name="language">
                 <div><b>Level:</b></div>
                 <label><input type="radio" name="level" value="senior">Senior</label>
                 <label><input type="radio" name="level" value="middle">Middle</label>
                 <label><input type="radio" name="level" value="junior" checked="checked">Junior</label><br>
                 <button type="submit">Update</button>
            </form>
    </body>
</html>