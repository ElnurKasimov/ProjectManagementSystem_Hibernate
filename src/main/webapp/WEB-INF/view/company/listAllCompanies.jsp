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
        <table>
             <thead>
                 <c:if test="${not empty companies}">
                     <tr>
                         <td>Company id</td>
                         <td>Company name</td>
                         <td>Rating</td>
                         </tr>
                 </c:if>
                 <c:if test="${empty companies}">
                     <p>There is no any company  in the database</p>
                 </c:if>
             </thead>
             <tbody>
                 <c:forEach var = "company" items="${companies}">
                     <tr>
                         <td>
                             <c:out value="${company.company_id}"/>
                         </td>
                         <td>
                             <c:out value="${company.company_name}"/>
                         </td>
                         <td>
                             <c:out value="${company.rating}"/>
                         </td>
                     </tr>
                 </c:forEach>
             </tbody>
        </table>
    </body>
</html>