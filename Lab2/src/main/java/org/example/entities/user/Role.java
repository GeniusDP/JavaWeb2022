package org.example.entities.user;

public enum Role {
  CLIENT,
  MANAGER,
  ADMIN;

  public static Role getRole(String value) {
    if(value == null){
      return null;
    }
    switch (value) {
      case "CLIENT": return CLIENT;
      case "MANAGER": return MANAGER;
      case "ADMIN": return ADMIN;
      default: return null;
    }
  }

}
