package org.example.security;

import org.example.entities.User;

public class SecurityContext {

  private static SecurityContext context;

  public static SecurityContext getContext(){
    if(context == null){
      context = new SecurityContext(null);
      return context;
    }
    return context;
  }

  private User user;

  private SecurityContext(User user) {
    this.user = user;
  }

  public void setCurrentUser(User user){
    this.user = user;
  }

}