package org.example.views.client;

import org.example.controllers.client.ClientAction;
import org.example.entities.car.Car;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.example.entities.receipt.Receipt;

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
    if(cars.size() == 0){
      printMessage("No such elements");
    }
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

  private void printMessage(Object value) {
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
    scanner.nextLine();
    printMessage("Do you need a driver? Write YES or NO: ");
    String driverNeeded = scanner.nextLine();
    return driverNeeded.equalsIgnoreCase("YES");
  }

  public void printReceipt(Receipt receipt) {
    printMessage("Grats! You have successfully created a receipt!");
    printMessage("Now you are able to see its status by calling appropriate command in menu.");
    printMessage(receipt);
  }

  public void printReceipts(String message, List<Receipt> receipts) {
    printMessage(message);
    if(receipts.size() == 0){
      printMessage("No such elements");
    }
    receipts.forEach(System.out::println);
  }

  public void printCarIsNotAvailable() {
    printMessage("Sorry, but this car is not available just right now.");
  }
}
