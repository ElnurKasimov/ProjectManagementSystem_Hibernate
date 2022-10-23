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
          width: 25%;
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
        <form action="/project/list_project_developers" method ="get">
                        <label for="projectName"> projectName: </label>
                        <input type="text" id="projectName" name="projectName"><br>
                        <button type="submit">Find</button>
                    </form>
        <c:out value="${result}"/>
         <c:if test="${not empty projects}">
             <table>
                 <thead>
                    <tr>
                        <td><b>Developer name</b></td>
                    </tr>
                 </thead>
                 <tbody>
                        <c:forEach var = "project" items="${projects}">
                            <tr>
                                <td>
                                    <c:out value="${project}"/>
                                </td>
                            </tr>
                        </c:forEach>
                 </tbody>
             </table>
          </c:if>
    </body>
</html>