package org.example.webcontrollers.admin;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import org.example.entities.user.User;
import org.example.exceptions.DatabaseException;
import org.example.repositories.UserRepository;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.UserSpecificDaoImpl;
import org.example.repositories.dbutils.ConnectionPool;
import org.example.services.UserService;

@Log4j
@WebServlet(name = "UnbanUserController", urlPatterns = "/menu/unban-user")
public class UnbanUserController extends HttpServlet {

  private UserService userService;

  @Override
  public void init() {
    CrudUserDao userDao = new CrudUserDao(ConnectionPool.getInstance());
    UserSpecificDaoImpl userSpecificDao = new UserSpecificDaoImpl(ConnectionPool.getInstance());
    UserRepository userRepository = new UserRepository(userDao, userSpecificDao);
    userService = new UserService(userRepository);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      long userId = Long.parseLong(request.getParameter("userId"));
      if (userService.existsById(userId)) {
        userService.unbanUser(userId);
        List<User> allUsers = userService.findAllUsers();
        request.setAttribute("users", allUsers);
        getServletContext().getRequestDispatcher("/pages/admin/all-users.jsp").forward(request, response);
      } else {
        getServletContext().getRequestDispatcher("/pages/error.jsp").forward(request, response);
      }
    } catch (DatabaseException e) {
      log.error("Ooops, 'banning user' operation failed due to some issue.");
      getServletContext().getRequestDispatcher("/pages/error.jsp").forward(request, response);
    }
  }
}
