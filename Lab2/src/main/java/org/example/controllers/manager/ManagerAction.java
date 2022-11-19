package org.example.controllers.manager;

public enum ManagerAction {
  SHOW_ALL_RECEIPTS,
  ACCEPT_RECEIPT,
  DECLINE_RECEIPT,
  RETURN_CAR,
  RETURN_DAMAGED_CAR,
  GO_OUT;

  public static ManagerAction getAction(String value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "SHOW_ALL_RECEIPTS" -> SHOW_ALL_RECEIPTS;
      case "ACCEPT_RECEIPT" -> ACCEPT_RECEIPT;
      case "DECLINE_RECEIPT" -> DECLINE_RECEIPT;
      case "RETURN_CAR" -> RETURN_CAR;
      case "RETURN_DAMAGED_CAR" -> RETURN_DAMAGED_CAR;
      default -> GO_OUT;
    };
  }
}
