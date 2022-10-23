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
           <form action="/company/add" method ="post">
               <label for="companyName"> Company name: </label>
               <input type="text" id="companyName" name="companyName"><br>
               <div>Company rating:</div>
               <label><input type="radio" name="rating" value="high">High</label>
               <label><input type="radio" name="rating" value="middle">Middle</label>
               <label><input type="radio" name="rating" value="low" checked="checked">Low</label><br>
               <button type="submit">Save</button>
           </form>
    </body>
</html>