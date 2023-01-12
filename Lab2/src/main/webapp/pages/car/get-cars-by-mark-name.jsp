<%--
  Created by IntelliJ IDEA.
  User: zaranik
  Date: 17/12/2022
  Time: 12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  table, th, td {
    border: 1px solid #96D4D4;
    border-collapse: collapse;
  }
</style>

<html>
  <head>
    <title>${caption}</title>
  </head>

  <body>
  <h3>${description}</h3>

    <form method="get" action="/menu/get-cars-by-mark-name">
      <input type="text" name="markName"/>
      <button type="submit">Search</button>
    </form>

    <c:if test="${cars == null}">
      <h1>No such cars</h1>
    </c:if>

    <c:if test="${cars != null}">
      <table>

        <tr style="background-color: #96D4D4">
          <th>Id</th>
          <th>Car name</th>
          <th>Mark</th>
          <th>Quality class</th>
          <th>Base price</th>
        </tr>

        <c:forEach var="car" items="${cars}">
          <tr>
            <th> ${car.id} </th>
            <th> ${car.name} </th>
            <th> ${car.mark.name} </th>
            <th> ${car.qualityClass} </th>
            <th> ${car.basePrice} </th>
          </tr>
        </c:forEach>

      </table>
    </c:if>
    <a href="/menu">Back to menu</a>
  </body>
</html>
