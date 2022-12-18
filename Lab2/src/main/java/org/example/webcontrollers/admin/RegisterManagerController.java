package org.example.webcontrollers.admin;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.entities.user.Role;
import org.example.entities.user.User;
import org.example.exceptions.DatabaseException;
import org.example.repositories.UserRepository;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.UserSpecificDaoImpl;
import org.example.repositories.dbutils.ConnectionPool;
import org.example.services.RegisterLoginService;
import org.example.services.UserService;
import org.example.webcontrollers.validators.RegistrationValidator;

@WebServlet(name = "RegisterManagerController", urlPatterns = "/menu/register-manager")
public class RegisterManagerController extends HttpServlet {

  private RegistrationValidator registrationValidator;
  private RegisterLoginService registerLoginService;
  private UserService userService;

  @Override
  public void init() {
    registrationValidator = new RegistrationValidator();
    CrudUserDao userDao = new CrudUserDao(ConnectionPool.getInstance());
    UserSpecificDaoImpl userSpecificDao = new UserSpecificDaoImpl(ConnectionPool.getInstance());
    UserRepository userRepository = new UserRepository(userDao, userSpecificDao);
    registerLoginService = new RegisterLoginService(userRepository);
    userService = new UserService(userRepository);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<User> allUsers = userService.findAllUsers();
    request.setAttribute("users", allUsers);
    getServletContext().getRequestDispatcher("/pages/admin/all-users.jsp").forward(request, response);
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
        registerLoginService.registerUser(username, password, firstName, lastName, Role.MANAGER);
        getServletContext().getRequestDispatcher("/pages/auth/register-success.jsp").forward(request, response);
        return;
      }
      getServletContext().getRequestDispatcher("/pages/auth/register-failed-due-to-validation.jsp").forward(request, response);
    } catch (DatabaseException e) {
      getServletContext().getRequestDispatcher("/pages/error.jsp").forward(request, response);
      System.out.println(e);
    }
  }


}
