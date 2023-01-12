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
    <title>My receipts</title>
  </head>

  <body>
    <c:if test="${receipts == null}">
      <h1>No receipts now</h1>
    </c:if>

    <c:if test="${receipts != null}">
      <table>

        <tr style="background-color: #96D4D4">
          <th>ID</th>
          <th>Receipt status</th>
          <th>Driver needed</th>
          <th>Days</th>
          <th>Total price</th>
          <th>Car ID</th>
          <th>Car name</th>
          <th>Username</th>
        </tr>

        <c:forEach var="receipt" items="${receipts}">
          <tr>
            <th>${receipt.id}</th>
            <th>${receipt.status}</th>
            <th>${receipt.driverNeeded}</th>
            <th>${receipt.daysNumber}</th>
            <th>${receipt.totalPrice}</th>
            <th>${receipt.car.id}</th>
            <th>${receipt.car.name}</th>
            <th>${username}</th>
          </tr>
        </c:forEach>

      </table>
    </c:if>
    <a href="/menu">Back to menu</a>
  </body>
</html>

