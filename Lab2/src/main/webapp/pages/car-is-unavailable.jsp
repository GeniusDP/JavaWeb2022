<%--
  Created by IntelliJ IDEA.
  User: zaranik
  Date: 17/12/2022
  Time: 14:24
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
    <title>Unavailable car</title>
  </head>
  <body>
    <h3>Receipt creating failed: this car is unavailable</h3>
    <table>
      <tr>
        <th> ${car.id} </th>
        <th> ${car.name} </th>
        <th> ${car.mark.name} </th>
        <th> ${car.qualityClass} </th>
        <th> ${car.basePrice} </th>
      </tr>
    </table>
    <a href="/menu">Back to menu</a>
  </body>
</html>
