<%--
  Created by IntelliJ IDEA.
  User: zaranik
  Date: 16/12/2022
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Menu</title>
</head>
<body>
  <h3>Main menu</h3>
  <p>Your role: ${userRole}</p>

  <div>
    <a href="/menu/get-cars-by-mark-name">Show cars by mark</a> </br>
    <a href="/menu/get-cars-by-class-name">Show cars by class</a> </br>
    <a href="/menu/get-cars-by-price">Show cars sorted by price</a> </br>
    <a href="/menu/get-client-receipts">Show my receipts</a> </br>
    <a href="/menu/rent-car-form">Rent a car</a> </br>
  </div>

</body>
</html>
