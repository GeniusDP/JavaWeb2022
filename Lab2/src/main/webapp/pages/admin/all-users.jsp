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
    <title>All users</title>
  </head>
  <body>
  <c:if test="${users != null}">
    <table>

      <tr style="background-color: #96D4D4">
        <th>ID</th>
        <th>Username</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Role</th>
        <th>Banned</th>
        <th>Ban</th>
        <th>Unban</th>
      </tr>

      <c:forEach var="user" items="${users}">
        <tr>
          <th>${user.id}</th>
          <th>${user.username}</th>
          <th>${user.firstName}</th>
          <th>${user.lastName}</th>
          <th>${user.role}</th>
          <th>${user.banned}</th>
          <th>
            <form action="/menu/ban-user" method="post">
              <input type="hidden" name="userId" value=${user.id}>
              <button type="submit">BAN</button>
            </form>
          </th>
          <th>
            <form action="/menu/unban-user" method="post">
              <input type="hidden" name="userId" value=${user.id}>
              <button type="submit">UNBAN</button>
            </form>
          </th>
        </tr>
      </c:forEach>

    </table>
  </c:if>
  <a href="/menu">Back to menu</a>
  </body>
</html>
