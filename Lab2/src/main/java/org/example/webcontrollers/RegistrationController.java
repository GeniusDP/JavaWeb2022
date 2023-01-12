package org.example.webcontrollers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.example.entities.user.Role;
import org.example.entities.user.User;
import org.example.exceptions.DatabaseException;
import org.example.repositories.UserRepository;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.UserSpecificDaoImpl;
import org.example.repositories.dbutils.ConnectionPool;
import org.example.services.RegisterLoginService;
import org.example.webcontrollers.validators.RegistrationValidator;

@Slf4j
@WebServlet(name = "RegistrationController", urlPatterns = "/register")
public class RegistrationController extends HttpServlet {

  private RegistrationValidator registrationValidator;
  private RegisterLoginService registerLoginService;

  @Override
  public void init() {
    registrationValidator = new RegistrationValidator();
    CrudUserDao userDao = new CrudUserDao(ConnectionPool.getInstance());
    UserSpecificDaoImpl userSpecificDao = new UserSpecificDaoImpl(ConnectionPool.getInstance());
    UserRepository userRepository = new UserRepository(userDao, userSpecificDao);
    registerLoginService = new RegisterLoginService(userRepository);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/pages/auth/register.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      String repeatPassword = request.getParameter("password-repeat");
      String firstName = request.getParameter("firstName");
      String lastName = request.getParameter("lastName");

      boolean valid = registrationValidator.validate(username, password, repeatPassword, firstName, lastName);
      if (valid) {
        User user = registerLoginService.registerUser(username, password, firstName, lastName, Role.CLIENT);
        if(user != null) {
          log.info("registered successfully");
          getServletContext().getRequestDispatcher("/pages/auth/register-success.jsp").forward(request, response);
        }
        log.info("registration failed: such user '{}' already exists", username);
        getServletContext().getRequestDispatcher("/pages/auth/register-failed-due-to-validation.jsp").forward(request, response);
        return;
      }
      log.info("registration failed: not valid request");
      getServletContext().getRequestDispatcher("/pages/auth/register-failed-due-to-validation.jsp").forward(request, response);
    } catch (DatabaseException e) {
      log.error(e.getMessage());
      getServletContext().getRequestDispatcher("/pages/error.jsp").forward(request, response);
    }
  }


}
