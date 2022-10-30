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
          text-align: center;
          padding: 3px;
        }
        tr:nth-child(even) {
          background-color: #dddddd;
        }
        </style>
    </head>
    <body>
        <c:import url="${contextPath}/WEB-INF/view/navigation.jsp"/>
         <form action="/customer/list_projects" method ="post">
             <label for="customerName"> Customer name: </label>
             <input type="text" id="customerName" name="customerName"><br>
             <button type="submit">Find</button>
         </form>
         <c:choose>
             <c:when test="${isPresent}">
                <table>
                     <thead>
                         <c:if test="${not empty projects}">
                             <tr>
                                 <td> Project id</td>
                                 <td>Project  name</td>
                             </tr>
                         </c:if>
                         <c:if test="${empty projects}">
                             <p>This customer has not ordered any project.</p>
                         </c:if>
                     </thead>
                     <tbody>
                         <c:forEach var = "project" items="${projects}">
                             <tr>
                                 <td>
                                     <c:out value="${project.project_id}"/>
                                 </td>
                                 <td>
                                     <c:out value="${project.projectName}"/>
                                 </td>
                             </tr>
                         </c:forEach>
                     </tbody>
                </table>
             </c:when>
             <c:otherwise>
                  <p>There is no customer with such name in the database.</p>
             </c:otherwise>
         </c:choose>
    </body>
</html>