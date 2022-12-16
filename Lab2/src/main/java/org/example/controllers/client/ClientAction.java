package org.example.controllers.client;

public enum ClientAction {
  SHOW_CARS_BY_MARK,
  SHOW_CARS_BY_CLASS,
  SORT_CARS_BY_PRICE,
  SORT_CARS_BY_NAME,
  SHOW_MY_RECEIPTS,
  RENT_A_CAR,
  GO_OUT;

  public static ClientAction getAction(String value) {
    if (value == null) {
      return null;
    }
    switch (value) {
      case "SHOW_CARS_BY_MARK": return SHOW_CARS_BY_MARK;
      case "SHOW_CARS_BY_CLASS": return SHOW_CARS_BY_CLASS;
      case "SORT_CARS_BY_PRICE": return SORT_CARS_BY_PRICE;
      case "SORT_CARS_BY_NAME": return SORT_CARS_BY_NAME;
      case "SHOW_MY_RECEIPTS": return SHOW_MY_RECEIPTS;
      case "RENT_A_CAR": return RENT_A_CAR;
      default: return GO_OUT;
    }
  }
}
