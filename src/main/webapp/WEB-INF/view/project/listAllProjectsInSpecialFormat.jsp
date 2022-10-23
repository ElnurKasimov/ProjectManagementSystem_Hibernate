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
          width: 40%;
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
        <c:if test="${not empty projects}">
        <c:out value="Database contains such projects :"/>
        <table>
              <thead>
                  <tr>
                      <td><b>start date  -  project name  -  a quantity developers in this project</b></td>
                  </tr>
            </thead>
            <tbody>
                <c:forEach var = "line" items="${projects}">
                    <tr>
                        <td>
                            <c:out value="${line}"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </c:if>
        <c:if test="${empty projects}">
            <p>Unfortunately, there is no any project  in the database</p>
        </c:if>
    </body>
</html>