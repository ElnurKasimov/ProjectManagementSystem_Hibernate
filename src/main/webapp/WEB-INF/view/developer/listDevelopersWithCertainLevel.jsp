<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style>
        table {
          font-family: arial, sans-serif;
          border-collapse: collapse;
          width: 30%;
        }
        td, th {
          border: 2px solid #dddddd;
          text-align: left;
          padding: 3px;
        }
        tr:nth-child(even) {
          background-color: #dddddd;
        }
        </style>
    </head>
    <body>
        <c:import url="${contextPath}/WEB-INF/view/navigation.jsp"/>
            <form action="/developer/level_developers">
                <label for="level"> Level: </label><br>
                <input type="text" id="level" name="level"><br>
                <button type="submit">Find</button>
            </form>
            <c:choose>
              <c:when test="${not empty list}">
             <table>
                 <thead>
                     <td><b><c:out value = "Developers  who has level '${level}' "/></b></td>
                 </thead>
                 <tbody>
                     <c:forEach var = "line" items="${list}">
                         <tr>
                             <td>
                                 <c:out value="${line}"/>
                             </td>
                         </tr>
                     </c:forEach>
                 </tbody>
             </table>
         </c:when>
           <c:otherwise>
             <c:out value="There are no developer who who has level '${level}' in the database."/>
           </c:otherwise>
         </c:choose>
    </body>
</html>