<%--
  Created by IntelliJ IDEA.
  User: zaranik
  Date: 17/12/2022
  Time: 14:03
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
    <title>Successfully rented</title>
  </head>
  <body>
    <h3>Car has been successfully rented</h3>
    <h4>Here is your receipt</h4>
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
      <tr>
        <th>${receipt.id}</th>
        <th>${receipt.status}</th>
        <th>${receipt.driverNeeded}</th>
        <th>${receipt.daysNumber}</th>
        <th>${receipt.totalPrice}</th>
        <th>${car.id}</th>
        <th>${car.name}</th>
        <th>${username}</th>
      </tr>
    </table>
    <a href="/menu">Back to menu</a>
  </body>
</html>
