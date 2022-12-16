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
    switch (value) {
      case "SHOW_ALL_RECEIPTS": return SHOW_ALL_RECEIPTS;
      case "ACCEPT_RECEIPT": return ACCEPT_RECEIPT;
      case "DECLINE_RECEIPT": return DECLINE_RECEIPT;
      case "RETURN_CAR": return RETURN_CAR;
      case "RETURN_DAMAGED_CAR": return RETURN_DAMAGED_CAR;
      default: return GO_OUT;
    }
  }
}
