package org.example.webcontrollers.filters;

import java.io.IOException;
import java.util.Base64;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.example.entities.user.Role;
import org.example.entities.user.User;
import org.example.repositories.UserRepository;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.UserSpecificDaoImpl;
import org.example.repositories.dbutils.ConnectionPool;
import org.example.services.RegisterLoginService;
import org.example.services.UserService;

public class UserInfoRequestAttributeFilter implements Filter {

  private RegisterLoginService registerLoginService;
  private UserService userService;

  @Override
  public void init(FilterConfig filterConfig) {
    CrudUserDao userDao = new CrudUserDao(ConnectionPool.getInstance());
    UserSpecificDaoImpl userSpecificDao = new UserSpecificDaoImpl(ConnectionPool.getInstance());
    UserRepository userRepository = new UserRepository(userDao, userSpecificDao);
    registerLoginService = new RegisterLoginService(userRepository);
    userService = new UserService(userRepository);
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    Cookie[] cookies = request.getCookies();

    Cookie basicAuth = null;
    if(cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("basicAuth")) {
          basicAuth = cookie;
          break;
        }
      }
    }

    if(basicAuth == null) {
      chain.doFilter(req, resp);
      return;
    }
    String cookieContent = new String(Base64.getDecoder().decode(basicAuth.getValue()));
    String[] credentials = cookieContent.split(":");

    if(credentials.length != 2){
      chain.doFilter(req, resp);
      return;
    }

    String username = credentials[0];
    String password = credentials[1];

    boolean authenticated = registerLoginService.login(username, password);
    if (!authenticated) {
      chain.doFilter(req, resp);
      return;
    }
    User user = userService.findByUsername(username);
    Role userRole = user.getRole();
    req.setAttribute("userRole", userRole);
    req.setAttribute("username", username);
    chain.doFilter(req, resp);
  }

  @Override
  public void destroy() {
  }

}
