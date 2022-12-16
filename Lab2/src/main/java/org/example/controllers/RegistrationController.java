package org.example.controllers;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Registration", urlPatterns = "/register")
public class RegistrationController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/pages/register.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println(req.getParameter("login"));
    System.out.println(req.getParameter("password"));
    System.out.println(req.getParameter("password-repeat"));
    getServletContext().getRequestDispatcher("/pages/register-success.jsp").forward(req, resp);
  }
}
