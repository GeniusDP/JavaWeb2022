package org.example.controllers.client;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.entities.Car;
import org.example.entities.Receipt;
import org.example.entities.User;
import org.example.entities.receipt.AbstractReceiptEntity;
import org.example.entities.receipt.BasicReceipt;
import org.example.entities.receipt.DaysRentAddition;
import org.example.entities.receipt.DriverAddition;
import org.example.entities.receipt.ReceiptEntity;
import org.example.exceptions.DatabaseException;
import org.example.security.SecurityContext;
import org.example.services.CarsService;
import org.example.services.ReceiptService;
import org.example.views.client.ClientView;

@RequiredArgsConstructor
public class ClientController {

  private final ClientView clientView;
  private final CarsService carsService;
  private final ReceiptService receiptService;

  public void start() {
    ClientAction action = clientView.chooseAction();
    switch (action) {
      case SHOW_CARS_BY_MARK -> getCarsByMark();
      case SHOW_CARS_BY_CLASS -> getCarsByClass();
      case SORT_CARS_BY_PRICE -> sortCarsByPrice();
      case SORT_CARS_BY_NAME -> sortCarsByName();
      case RENT_A_CAR -> rentACar();
    }
  }


  //template method + decorator
  private void rentACar() {
    try {
      long carId = clientView.getCarId();
      if (carsService.existsById(carId)) {
        Car car = carsService.getCarById(carId);
        User user = SecurityContext.getContext().getSubject();
        Receipt receipt = Receipt.builder().car(car).user(user).build();

        AbstractReceiptEntity receiptEntity = new BasicReceipt(car, user);

        receiptEntity = applyAdditions(receiptEntity, receipt);

        ReceiptEntity resultReceiptEntity = new ReceiptEntity(receiptEntity);
        receipt.setTotalPrice(resultReceiptEntity.getTotalPrice());
        receiptService.registerReceipt(receipt);
        clientView.printReceipt(receipt);
      } else {
        clientView.printNoCarWithSuchIdFound();
      }
    } catch (DatabaseException e) {
      System.out.println("Ooops, rent operation failed due to some issue.");
    }

  }

  protected AbstractReceiptEntity applyAdditions(AbstractReceiptEntity receiptEntity,
      Receipt receipt) {
    int daysRent = clientView.getDaysRent();
    receiptEntity = new DaysRentAddition(receiptEntity, daysRent);
    receipt.setDaysNumber(daysRent);

    boolean needDriver = clientView.needDriver();
    receipt.setDriverNeeded(needDriver);
    if (needDriver) {
      receiptEntity = new DriverAddition(receiptEntity);
    }
    return receiptEntity;
  }

  private void sortCarsByName() {
    try {
      List<Car> carsSortedByName = carsService.getCarsSortedByName();
      clientView.printCars("Cars sorted by name: ", carsSortedByName);
    } catch (DatabaseException e) {
      System.out.println("Ooops, 'sorting cars by name' operation failed due to some issue.");
    }
  }

  private void sortCarsByPrice() {
    try {
      List<Car> carsSortedByPrice = carsService.getCarsSortedByPrice();
      clientView.printCars("Cars sorted by price: ", carsSortedByPrice);
    } catch (DatabaseException e) {
      System.out.println("Ooops, 'sorting cars by price' operation failed due to some issue.");
    }
  }

  private void getCarsByClass() {
    try {
      String qualityClass = clientView.getCarClass();
      List<Car> carsByQualityClass = carsService.getCarsByQualityClass(qualityClass);
      clientView.printCars("Cars by quality class name", carsByQualityClass);
    } catch (DatabaseException e) {
      System.out.println(
          "Ooops, 'sorting cars by quality class' operation failed due to some issue.");
    }
  }

  private void getCarsByMark() {
    try {
      String markName = clientView.getCarMark();
      List<Car> carsByMarkName = carsService.getCarsByMark(markName);
      clientView.printCars("Cars by mark name: ", carsByMarkName);
    } catch (DatabaseException e) {
      System.out.println("Ooops, 'sorting cars by mark' operation failed due to some issue.");
    }
  }

}
