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
          width: 50%;
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
        <c:import url="${contextPath}/WEB-INF/view/navigation.jsp"/>
        <table>
            <thead>
                <c:if test="${not empty projects}">
                    <tr>
                        <td>Project id</td>
                        <td>Project name</td>
                        <td>It Company which develops</td>
                        <td>Customer which orders</td>
                        <td>Cost</td>
                        <td>Start date</td>
                    </tr>
                </c:if>
                <c:if test="${empty projects}">
                    <p>There is no any project  in the database</p>
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
                        <td>
                            <c:out value="${project.company.companyName}"/>
                        </td>
                        <td>
                            <c:out value="${project.customer.customerName}"/>
                        </td>
                         <td>
                             <c:out value="${project.cost}"/>
                         </td>
                           <td>
                               <c:out value="${project.startDate}"/>
                           </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>