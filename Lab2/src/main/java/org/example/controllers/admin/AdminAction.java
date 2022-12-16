package org.example.controllers.admin;

public enum AdminAction {
  REGISTER_MANAGER,
  BAN_USER,
  UNBAN_USER,
  CREATE_CAR,
  REMOVE_CAR,
  SHOW_ALL_CARS,
  GO_OUT;

  public static AdminAction getAction(String value) {
    if (value == null) {
      return null;
    }
    switch (value) {
      case "REGISTER_MANAGER": return REGISTER_MANAGER;
      case "BAN_USER":return  BAN_USER;
      case "UNBAN_USER":return  UNBAN_USER;
      case "CREATE_CAR": return CREATE_CAR;
      case "REMOVE_CAR": return REMOVE_CAR;
      case "SHOW_ALL_CARS":return  SHOW_ALL_CARS;
      default: return GO_OUT;
    }
  }
}
