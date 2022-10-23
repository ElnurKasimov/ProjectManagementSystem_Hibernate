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
            <form action="/project/add" method="post">
                <label for="projectName">Project name:</label>
                <input type="text" id="projectName" name="projectName"><br>
                <label for="customerName">Customer which ordered the project:</label>
                <input type="text" id="customerName" name="customerName"><br>
                <label for="cost">Budget of the project (only digits):</label>
                <input type="text" id="cost" name="cost"><br>
                <label for="companyName">Company which develop the project: </label>
                <input type="text" id="companyName" name="companyName"><br>
                <label for="startDate">Start date of the project (in format yyyy-mm-dd):</label>
                <input type="text" id="startDate" name="startDate"><br>
                <button type="submit">Save</button>
            </form>
        <c:out value="${result}"/>
    </body>
</html>