package org.example.controllers.manager;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.entities.receipt.Receipt;
import org.example.exceptions.DatabaseException;
import org.example.services.ReceiptService;
import org.example.views.manager.ManagerView;

@RequiredArgsConstructor
public class ManagerController {

  private final ManagerView managerView;
  private final ReceiptService receiptService;

  public void start() {
    ManagerAction action = managerView.chooseAction();
    switch (action) {
      case SHOW_ALL_RECEIPTS -> showAllReceipts();
      case RETURN_CAR -> returnACar();
      case ACCEPT_RECEIPT -> acceptReceipt();
      case DECLINE_RECEIPT -> declineReceipt();
      case RETURN_DAMAGED_CAR -> returnDamagedCar();
    }
  }

  private void showAllReceipts() {
    List<Receipt> allReceipts = receiptService.getAllReceipts();
    managerView.printReceipts("Here are all receipts.", allReceipts);
  }

  private void returnACar() {
    managerView.returningCar();
    try {
      long receiptId = managerView.getReceiptId();
      if (receiptService.existsById(receiptId)) {
        boolean result = receiptService.returnCar(receiptId);
        if (result) {
          managerView.returnedCarSuccessfully(receiptId);
          return;
        }
        managerView.failedToReturnCar();
      } else {
        managerView.noSuchReceipt();
      }
    } catch (DatabaseException e) {
      System.out.println("Ooops, returning a car operation failed due to some issue.");
    }

  }

  private void acceptReceipt() {
    managerView.acceptingCar();
    try {
      long receiptId = managerView.getReceiptId();
      if (receiptService.existsById(receiptId)) {
        boolean result = receiptService.acceptCar(receiptId);
        if (result) {
          managerView.acceptedCarSuccessfully(receiptId);
          return;
        }
        managerView.failedToAcceptCar();
      } else {
        managerView.noSuchReceipt();
      }
    } catch (DatabaseException e) {
      System.out.println("Ooops, returning a car operation failed due to some issue.");
    }

  }

  private void declineReceipt() {
    managerView.decliningCar();
    try {
      long receiptId = managerView.getReceiptId();
      if (receiptService.existsById(receiptId)) {
        String message = managerView.getDeclineMessage();
        boolean result = receiptService.declineReceipt(receiptId, message);
        if (result) {
          managerView.declinedCarSuccessfully(receiptId);
          return;
        }
        managerView.failedToAcceptCar();
      } else {
        managerView.noSuchReceipt();
      }
    } catch (DatabaseException e) {
      System.out.println("Ooops, returning a car operation failed due to some issue.");
    }
  }

  private void returnDamagedCar() {
    managerView.returnDamagedCar();
    try {
      long receiptId = managerView.getReceiptId();
      if (receiptService.existsById(receiptId)) {
        int fixPrice = managerView.getFixPrice();
        boolean result = receiptService.returnDamagedCar(receiptId, fixPrice);
        if (result) {
          managerView.returnedCarSuccessfully(receiptId);
          return;
        }
        managerView.failedToReturnCar();
      } else {
        managerView.noSuchReceipt();
      }
    } catch (DatabaseException e) {
      System.out.println("Ooops, returning a car operation failed due to some issue.");
    }
  }

}
