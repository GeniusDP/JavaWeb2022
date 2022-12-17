<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>
  <head>
    <meta charset="UTF-8">
    <title>SimpleServlet</title>
    <link rel="icon" type="image/png" href="favicon.png">
  </head>
  <body>
    <h1>Car renting system</h1>
    <p>We are pleasant to promise you our service</p>
    <c:if test="${username != null}">
      <h4>Your username: ${username}</h4>
      <h4>Your role: ${userRole}</h4>
    </c:if>
    <a href="/register">Register</a> </br>
    <c:if test="${username == null}">
      <a href="/login">Login</a> </br>
    </c:if>
    <c:if test="${username != null}">
      <a href="/logout">Logout</a> </br>
    </c:if>
    <a href="/menu">Main menu</a> </br>
  </body>
</html>