package org.example.controllers.client;

public enum ClientAction {
  SHOW_CARS_BY_MARK,
  SHOW_CARS_BY_CLASS,
  SORT_CARS_BY_PRICE,
  SORT_CARS_BY_NAME,
  LOG_OUT;

  public static ClientAction getAction(String value){
    if(value == null){
      return null;
    }
    return switch (value){
      case "SHOW_CARS_BY_MARK" -> SHOW_CARS_BY_MARK;
      case "SHOW_CARS_BY_CLASS" -> SHOW_CARS_BY_CLASS;
      case "SORT_CARS_BY_PRICE" -> SORT_CARS_BY_PRICE;
      case "SORT_CARS_BY_NAME" -> SORT_CARS_BY_NAME;
      default -> LOG_OUT;
    };
  }
}
