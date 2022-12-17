<%--
  Created by IntelliJ IDEA.
  User: zaranik
  Date: 16/12/2022
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
</head>
<body>
  <h3>Login form</h3>
  <form action="/login" method="post">
    <label for="username-input">Username</label>
    <input id="username-input" name="username"/>
    </br>

    <label for="password-input">Password</label>
    <input id="password-input" name="password" type="password"/>
    </br>

    <button type="submit">Login</button>
  </form>
  <a href="/">Back to main page</a>
</body>
</html>
