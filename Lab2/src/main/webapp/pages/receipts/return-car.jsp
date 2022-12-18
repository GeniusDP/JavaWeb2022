<%--
  Created by IntelliJ IDEA.
  User: zaranik
  Date: 17/12/2022
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Return car</title>
  </head>
  <body>
    <h3>Returning car of receipt with ID = ${receiptId}</h3>
    <form action="/menu/return-car" method="post">
      <label for="fixingPrice">Fixing Price (if returned damaged)</label>
      <input type="hidden" name="receiptId" value=${receiptId}>
      <input type="number" min="0" id="fixingPrice" name="fixingPrice" placeholder="fixing price"/>
      <button type="submit">RETURN CAR</button>
    </form>
  </body>
</html>
