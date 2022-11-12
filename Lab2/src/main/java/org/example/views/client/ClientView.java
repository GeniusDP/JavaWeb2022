package org.example.views.client;

import org.example.controllers.client.ClientAction;
import org.example.entities.Car;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientView {

  private final Scanner scanner = new Scanner(System.in);

  public ClientAction chooseAction() {
    System.out.println("Write action what to do. All variants: ");
    Arrays.stream(ClientAction.values()).forEach(System.out::println);
    String value = scanner.nextLine();
    return ClientAction.getAction(value);
  }

  public String getCarMark() {
    System.out.println("Write mark name:");
    return scanner.nextLine();
  }

  public void printCars(String message, List<Car> cars) {
    System.out.println(message);
    cars.forEach(System.out::println);
  }

  public String getCarClass() {
    System.out.println("Write quality class name:");
    return scanner.nextLine();
  }

}
