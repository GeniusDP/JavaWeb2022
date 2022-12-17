<%--
  Created by IntelliJ IDEA.
  User: zaranik
  Date: 17/12/2022
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Renting a car</title>
</head>
  <body>

    <h3>Renting a car</h3>
    <form action="/menu/rent-car" method="post">

      <label for="carId" >Car ID</label>
      <input type="number" min="1" name="carId" id="carId"/>

      <label for="driverNeeded" >Driver needed</label>
      <input type="checkbox" name="driverNeeded" id="driverNeeded"/>

      <label for="daysRent" >Days</label>
      <input type="number" min="1" name="daysRent" id="daysRent"/>

      <button type="submit">Rent a car</button>
    </form>
    <a href="/menu">Back to menu</a>
  </body>
</html>
