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
         <c:if test="${not empty projects}">
            <form action="/developer/add_developer_relations" method="post">
               <p hidden>
                     <input id="lastName" name="lastName" value =${lastName}>
                     <input  id="firstName" name="firstName" value = ${firstName}>
               </p>
               <p> The company You choose develops projects listed below . Choose those in which developer participates.</p>
                <c:forEach var = "project" items="${projects}">
                  <input type="checkbox" name="projectName" value="${project.project_name}">${project.project_name}</input><br/>
                </c:forEach><br>
                <label for="language">Language:</label>
                <input type="text" id="language" name="language">
                <div><b>Level:</b></div>
                <label><input type="radio" name="level" value="senior">Senior</label>
                <label><input type="radio" name="level" value="middle">Middle</label>
                <label><input type="radio" name="level" value="junior" checked="checked">Junior</label><br>
                <button type="submit">Save</button>
            </form>
         </c:if>
         <c:if test="${empty projects}">
              <pre>
The company You choose does not develop any project.
So add some project to the company.
Developer was added to the database without any projects and skills relations.</pre>
         </c:if>
    </body>
</html>