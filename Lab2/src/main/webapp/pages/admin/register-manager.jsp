<%--
  Created by IntelliJ IDEA.
  User: zaranik
  Date: 18/12/2022
  Time: 09:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Register manager</title>
  </head>
  <body>
    <h3>Registration of new manager in the system</h3>
    <form action="/menu/register-manager" method="post">
      <label for="username-input">Username</label>
      <input id="username-input" name="username"/>
      </br>

      <label for="password-input">Password</label>
      <input id="password-input" name="password" type="password"/>
      </br>

      <label for="password-repeat-input">Repeat password</label>
      <input id="password-repeat-input" name="password-repeat" type="password"/>
      </br>


      <label for="first-name-input">First name</label>
      <input id="first-name-input" name="firstName"/>
      </br>

      <label for="last-name-input">Last name</label>
      <input id="last-name-input" name="lastName"/>
      </br>

      <button type="submit">Register now!</button>
    </form>
    <a href="/">Back to main page</a>
  </body>
</html>
