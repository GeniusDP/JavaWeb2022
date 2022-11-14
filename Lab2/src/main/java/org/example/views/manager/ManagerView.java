package org.example.views.manager;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.example.controllers.manager.ManagerAction;
import org.example.entities.receipt.Receipt;

public class ManagerView {

  private final Scanner scanner = new Scanner(System.in);

  public ManagerAction chooseAction() {
    printMessage("Write action what to do. All variants: ");
    Arrays.stream(ManagerAction.values()).forEach(this::printMessage);
    String value = scanner.nextLine();
    return ManagerAction.getAction(value);
  }

  public void printReceipts(String message, List<Receipt> receipts) {
    printMessage(message);
    if (receipts.size() == 0) {
      printMessage("No such elements");
    }
    receipts.forEach(System.out::println);
  }


  public long getReceiptId() {
    printMessage("Write receipt id:");
    return scanner.nextLong();
  }


  public void printMessage(String message) {
    System.out.println(message);
  }

  private void printMessage(Object value) {
    printMessage(value.toString());
  }

  public void failedToReturnCar() {
    printMessage("Failed to return a car!");
  }

  public void noSuchReceipt() {
    printMessage("No such receipt!");
  }

  public void returnedCarSuccessfully(long receiptId) {
    printMessage("Car from receipt with receiptId = " + receiptId + " successfully returned.");
  }

  public void returningCar() {
    printMessage("Returning car operation:");
  }

  public void acceptingCar() {
    printMessage("Accepting car operation:");
  }

  public void acceptedCarSuccessfully(long receiptId) {
    printMessage("Car from receipt with receiptId = " + receiptId + " successfully accepted.");
  }

  public void failedToAcceptCar() {
    printMessage("Failed to accept a car!");
  }

  public void decliningCar() {
    printMessage("Declining car operation:");
  }

  public String getDeclineMessage() {
    scanner.nextLine();
    printMessage("Write decline message: ");
    return scanner.nextLine();
  }

  public void declinedCarSuccessfully(long receiptId) {
    printMessage("Receipt with receiptId = " + receiptId + " successfully declined.");
  }

  public void returnDamagedCar() {
    printMessage("Returning damaged car operation:");
  }

  public int getFixPrice() {
    printMessage("Write fix price:");
    return scanner.nextInt();
  }
}
