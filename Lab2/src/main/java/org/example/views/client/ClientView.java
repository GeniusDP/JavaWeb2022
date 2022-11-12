package org.example.views.client;

import org.example.controllers.client.ClientAction;
import org.example.entities.Car;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientView {

  private final Scanner scanner = new Scanner(System.in);

  public ClientAction chooseAction() {
    printMessage("Write action what to do. All variants: ");
    Arrays.stream(ClientAction.values()).forEach(this::printMessage);
    String value = scanner.nextLine();
    return ClientAction.getAction(value);
  }

  public String getCarMark() {
    printMessage("Write mark name:");
    return scanner.nextLine();
  }

  public void printCars(String message, List<Car> cars) {
    printMessage(message);
    cars.forEach(System.out::println);
  }

  public String getCarClass() {
    printMessage("Write quality class name:");
    return scanner.nextLine();
  }

  public int getCarId() {
    printMessage("Write car id:");
    return scanner.nextInt();
  }

  public void printMessage(String message) {
    System.out.println(message);
  }

  public void printMessage(Object value) {
    printMessage(value.toString());
  }

  public void printNoCarWithSuchIdFound() {
    printMessage("No car with such ID:(");
  }

  public int getDaysRent() {
    printMessage("How much days of rent do you need? Write, pls:");
    int days;
    while ((days = scanner.nextInt()) <= 0){
      printMessage("");
    }
    return days;
  }

  public boolean needDriver() {
    printMessage("Do you need a driver? Write YES or NO: ");
    String driverNeeded =

  }
}
