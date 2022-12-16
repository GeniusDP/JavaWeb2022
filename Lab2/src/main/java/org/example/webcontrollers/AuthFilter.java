package org.example.webcontrollers;

import java.io.IOException;
import java.util.Arrays;
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


@WebFilter(filterName = "AuthFilter" , urlPatterns = {"/auth"})
public class AuthFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;
    Cookie[] cookies = request.getCookies();
    boolean authenticated = Arrays.stream(cookies).anyMatch(cookie -> cookie.getName().equals("basicAuth"));
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
