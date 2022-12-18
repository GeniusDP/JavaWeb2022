<%--
  Created by IntelliJ IDEA.
  User: zaranik
  Date: 16/12/2022
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Menu</title>
  </head>
  <body>
    <h2>Main menu</h2>

    <c:if test="${username != null}">
      <h4>Your username: ${username}</h4>
      <h4>Your role: ${userRole}</h4>
    </c:if>

    <div>
      <a href="/menu/get-cars-by-mark-name">Show cars by mark</a> </br>
      <a href="/menu/get-cars-by-class-name">Show cars by class</a> </br>
      <a href="/menu/get-cars-by-price">Show cars sorted by price</a> </br>

      <c:if test="${userRole == 'CLIENT'}">
        <a href="/menu/my-receipts">My receipts</a> </br>
        <a href="/menu/rent-car">Rent a car</a> </br>
      </c:if>

      <c:if test="${userRole == 'MANAGER'}">
        <a href="/menu/all-receipts">All receipts</a> </br>
      </c:if>

      <c:if test="${userRole == 'ADMIN'}">
        <a href="/menu/register-manager">Register new manager</a> </br>
        <a href="/menu/all-users">Manage users</a> </br>
      </c:if>

      <a href="/">Main Page</a> </br>
    </div>

  </body>
</html>
