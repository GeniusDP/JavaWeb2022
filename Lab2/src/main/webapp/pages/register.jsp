<%--
  Created by IntelliJ IDEA.
  User: zaranik
  Date: 16/12/2022
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Registration</title>
</head>
<body>

<h3>Registration form</h3>
<form action="/register" method="post">
  <label for="login-input">login</label>
  <input id="login-input" name="login"/>
  </br>

  <label for="password-input">password</label>
  <input id="password-input" name="password" type="password"/>
  </br>

  <label for="password-repeat-input">repeat password</label>
  <input id="password-repeat-input" name="password-repeat" type="password"/>
  </br>

  <button type="submit">Register now!</button>
</form>

</body>
</html>
