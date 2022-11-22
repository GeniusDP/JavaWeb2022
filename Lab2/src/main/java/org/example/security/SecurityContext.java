package org.example.security;

import org.example.entities.user.User;

public class SecurityContext {

  private static SecurityContext context;

  public static SecurityContext getContext(){
    if(context == null){
      context = new SecurityContext(null);
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

  public boolean isEmpty(){
    return user == null;
  }

  public User getSubject(){
    return user;
  }

  public void clear() {
    context = null;
  }
}
