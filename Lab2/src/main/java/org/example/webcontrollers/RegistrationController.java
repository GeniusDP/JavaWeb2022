package org.example.webcontrollers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.entities.user.Role;
import org.example.repositories.UserRepository;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.UserSpecificDaoImpl;
import org.example.repositories.dbutils.ConnectionPool;
import org.example.services.RegisterLoginService;
import org.example.webcontrollers.validators.RegistrationValidator;

@WebServlet(name = "RegistrationController" , urlPatterns = "/register")
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
    getServletContext().getRequestDispatcher("/pages/register.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String repeatPassword = request.getParameter("password-repeat");
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");

    boolean valid = registrationValidator.validate(username, password, repeatPassword, firstName, lastName);
    if (valid) {
      registerLoginService.registerUser(username, password, firstName, lastName, Role.CLIENT);
      getServletContext().getRequestDispatcher("/pages/register-success.jsp").forward(request, response);
    }
    getServletContext().getRequestDispatcher("/pages/register-failed-due-to-validation.jsp").forward(request, response);
  }


}
