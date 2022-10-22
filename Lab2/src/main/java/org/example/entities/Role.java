package org.example.entities;

public enum Role {
  CLIENT,
  MANAGER,
  ADMIN;

  public static Role getRole(String value) {
    return switch (value) {
      case "CLIENT" -> CLIENT;
      case "MANAGER" -> MANAGER;
      case "ADMIN" -> ADMIN;
      default -> null;
    };
  }

}
