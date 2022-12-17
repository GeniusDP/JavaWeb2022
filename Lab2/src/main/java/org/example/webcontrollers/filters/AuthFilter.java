package org.example.webcontrollers.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.repositories.UserRepository;
import org.example.repositories.dao.cruddao.CrudUserDao;
import org.example.repositories.dao.specificdao.UserSpecificDaoImpl;
import org.example.repositories.dbutils.ConnectionPool;
import org.example.services.RegisterLoginService;


@WebFilter(filterName = "AuthFilter" , urlPatterns = {"/auth"})
public class AuthFilter implements Filter {

  private RegisterLoginService registerLoginService;

  @Override
  public void init(FilterConfig filterConfig) {
    CrudUserDao userDao = new CrudUserDao(ConnectionPool.getInstance());
    UserSpecificDaoImpl userSpecificDao = new UserSpecificDaoImpl(ConnectionPool.getInstance());
    UserRepository userRepository = new UserRepository(userDao, userSpecificDao);
    registerLoginService = new RegisterLoginService(userRepository);
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;
    Cookie[] cookies = request.getCookies();

    Cookie basicAuth = null;
    for (Cookie cookie : cookies) {
      if(cookie.getName().equals("basicAuth")){
        basicAuth = cookie;
        break;
      }
    }

    if(basicAuth == null) {
      response.sendRedirect("/login");
      return;
    }
    String cookieContent = new String(Base64.getDecoder().decode(basicAuth.getValue()));
    String[] credentials = cookieContent.split(":");

    if(credentials.length != 2){
      response.sendRedirect("/login");
      return;
    }

    String username = credentials[0];
    String password = credentials[1];

    boolean authenticated = registerLoginService.login(username, password);
    if (!authenticated) {
      response.sendRedirect("/login");
      return;
    }
    chain.doFilter(req, resp);
  }

  @Override
  public void destroy() {
  }
}
