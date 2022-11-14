package org.example.views.admin;

import java.util.Arrays;
import java.util.Scanner;
import org.example.controllers.admin.AdminAction;
import org.example.controllers.client.ClientAction;

public class AdminView {
  private final Scanner scanner = new Scanner(System.in);

  public AdminAction chooseAction() {
    printMessage("Write action what to do. All variants: ");
    Arrays.stream(ClientAction.values()).forEach(this::printMessage);
    String value = scanner.nextLine();
    return AdminAction.getAction(value);
  }

  public void printMessage(String message) {
    System.out.println(message);
  }

  private void printMessage(Object value) {
    printMessage(value.toString());
  }

}
