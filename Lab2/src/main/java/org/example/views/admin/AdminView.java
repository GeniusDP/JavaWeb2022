package org.example.views.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.example.controllers.admin.AdminAction;
import org.example.entities.car.Car;
import org.example.entities.car.QualityClass;

public class AdminView {
  private final Scanner scanner = new Scanner(System.in);

  public AdminAction chooseAction() {
    printMessage("Write action what to do. All variants: ");
    Arrays.stream(AdminAction.values()).forEach(this::printMessage);
    String value = scanner.nextLine();
    return AdminAction.getAction(value);
  }

  public void printMessage(String message) {
    System.out.println(message);
  }

  private void printMessage(Object value) {
    printMessage(value.toString());
  }

  public void printCars(String message, List<Car> cars) {
    printMessage(message);
    if(cars.size() == 0){
      printMessage("No such elements");
    }
    cars.forEach(System.out::println);
  }

  public void registrationStart() {
    System.out.println("REGISTRATION OF MANAGER");
  }

  public String getUsername() {
    System.out.println("enter manager`s username and press enter: ");
    return scanner.nextLine();
  }

  public String getPassword() {
    System.out.println("enter manager`s password and press enter: ");
    return scanner.nextLine();
  }

  public String getFirstName() {
    System.out.println("enter manager`s first name and press enter: ");
    return scanner.nextLine();
  }

  public String getLastName() {
    System.out.println("enter manager`s last name and press enter: ");
    return scanner.nextLine();
  }

  public void managerRegistrationSuccessful() {
    printMessage("Registered manager successfully!");
  }

  public void banningUserStart() {
    printMessage("Banning user operation");
  }

  public long getUserId() {
    printMessage("Write user id:");
    return scanner.nextLong();
  }

  public void noSuchUserFound() {
    printMessage("No such user!");
  }

  public void unbanningUserStart() {
    printMessage("Unbanning user operation");
  }

  public void unbanningSuccessful() {
    printMessage("Unbanning user successfully!");
  }

  public void banningSuccessful() {
    printMessage("Banning user successfully!");
  }

  public void removingCarStart() {
    printMessage("Removing car operation started");
  }

  public long getCarId() {
    printMessage("Write car id:");
    return scanner.nextLong();
  }

  public void removingCarSuccessfully() {
    printMessage("Removed car successfully!");
  }

  public void noSuchCarFound() {
    printMessage("No such car!");
  }

  public void creatingCarStart() {
    printMessage("Creating operation started");
  }

  public String getCarName() {
    printMessage("Write car name:");
    return scanner.nextLine();
  }

  public void carAlreadyExists() {
    printMessage("Such car already exists!");
  }

  public long getMarkId() {
    printMessage("Write mark id: ");
    return scanner.nextLong();
  }

  public void noSuchMarkFound() {
    printMessage("No such mark found");
  }

  public int getBasePrice() {
    printMessage("Write base price: ");
    return scanner.nextInt();
  }

  public QualityClass getQualityClass() {
    printMessage("Write qualityClass: ");
    scanner.nextLine();
    return QualityClass.getQualityClass(scanner.nextLine());
  }

  public void creatingCarSuccessfully() {
    printMessage("Created car successfully!");
  }
}
