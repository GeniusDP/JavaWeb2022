package org.example.webcontrollers;

import java.io.IOException;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LogoutController" , urlPatterns = "/logout")
public class LogoutController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    getServletContext().getRequestDispatcher("/pages/auth/logout-confirm.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String cookieValue = ":";
    String cookieValueBase64 = Base64.getEncoder().encodeToString(cookieValue.getBytes());
    Cookie cookie = new Cookie("basicAuth", cookieValueBase64);
    cookie.setHttpOnly(true);
    response.addCookie(cookie);
    response.sendRedirect("/");
  }
}
