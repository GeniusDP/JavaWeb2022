package org.example.webcontrollers;

import java.io.IOException;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.DatabaseException;
import org.example.repositories.UserRepository;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.UserSpecificDaoImpl;
import org.example.repositories.dbutils.ConnectionPool;
import org.example.services.RegisterLoginService;
import org.example.webcontrollers.validators.LoginValidator;

@Log4j
@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {

  private LoginValidator loginValidator;
  private RegisterLoginService registerLoginService;

  @Override
  public void init() {
    log.info("LoginController init method invoked");
    loginValidator = new LoginValidator();
    CrudUserDao userDao = new CrudUserDao(ConnectionPool.getInstance());
    UserSpecificDaoImpl userSpecificDao = new UserSpecificDaoImpl(ConnectionPool.getInstance());
    UserRepository userRepository = new UserRepository(userDao, userSpecificDao);
    registerLoginService = new RegisterLoginService(userRepository);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/pages/auth/login.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String username = request.getParameter("username");
      String password = request.getParameter("password");

      boolean valid = loginValidator.validate(username, password);
      if (!valid) {
        getServletContext()
          .getRequestDispatcher("/pages/auth/login-failed-due-to-validation.jsp")
          .forward(request, response);
        return;
      }

      boolean login = registerLoginService.login(username, password);
      if (!login) {
        getServletContext()
          .getRequestDispatcher("/pages/auth/login-failed-wrong-input.jsp")
          .forward(request, response);
        return;
      }

      String cookieValue = username + ":" + password;
      String cookieValueBase64 = Base64.getEncoder().encodeToString(cookieValue.getBytes());
      Cookie cookie = new Cookie("basicAuth", cookieValueBase64);
      cookie.setHttpOnly(true);
      response.addCookie(cookie);

      response.sendRedirect("/menu");
    } catch (DatabaseException e) {
      log.error(e.getMessage());
      getServletContext().getRequestDispatcher("/pages/error.jsp").forward(request, response);
    }
  }

}
