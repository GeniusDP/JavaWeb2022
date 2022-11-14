package org.example.controllers.admin;

public enum AdminAction {
  REGISTER_MANAGER,
  BAN_USER,
  UNBAN_USER,
  CREATE_CAR,
  REMOVE_CAR,
//  CREATE_MARK,
//  REMOVE_MARK,
//  SHOW_ALL_MARKS,
  SHOW_ALL_CARS,
  GO_OUT;

  public static AdminAction getAction(String value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "REGISTER_MANAGER" -> REGISTER_MANAGER;
      case "BAN_USER" -> BAN_USER;
      case "UNBAN_USER" -> UNBAN_USER;
      case "CREATE_CAR" -> CREATE_CAR;
      case "REMOVE_CAR" -> REMOVE_CAR;
//      case "CREATE_MARK" -> CREATE_MARK;
//      case "REMOVE_MARK" -> REMOVE_MARK;
//      case "SHOW_ALL_MARKS" -> SHOW_ALL_MARKS;
      case "SHOW_ALL_CARS" -> SHOW_ALL_CARS;
      default -> GO_OUT;
    };
  }
}
