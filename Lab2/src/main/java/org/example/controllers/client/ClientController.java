package org.example.controllers.client;

import lombok.RequiredArgsConstructor;
import org.example.entities.Car;
import org.example.entities.Receipt;
import org.example.entities.User;
import org.example.entities.receipt.*;
import org.example.security.SecurityContext;
import org.example.services.CarsService;
import org.example.services.ReceiptService;
import org.example.views.client.ClientView;

import java.util.List;

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

  }

  protected AbstractReceiptEntity applyAdditions(AbstractReceiptEntity receiptEntity, Receipt receipt) {
    int daysRent = clientView.getDaysRent();
    receiptEntity = new DaysRentAddition(receiptEntity, daysRent);
    receipt.setDaysNumber(daysRent);

    boolean needDriver = clientView.needDriver();
    receipt.setDriverNeeded(needDriver);
    if(needDriver){
      receiptEntity = new DriverAddition(receiptEntity);
    }
    return receiptEntity;
  }

  private void sortCarsByName() {
    List<Car> carsSortedByName = carsService.getCarsSortedByName();
    clientView.printCars("Cars sorted by name: ", carsSortedByName);

  }

  private void sortCarsByPrice() {
    List<Car> carsSortedByPrice = carsService.getCarsSortedByPrice();
    clientView.printCars("Cars sorted by price: ", carsSortedByPrice);

  }

  private void getCarsByClass() {
    String qualityClass = clientView.getCarClass();
    List<Car> carsByQualityClass = carsService.getCarsByQualityClass(qualityClass);
    clientView.printCars("Cars by quality class name", carsByQualityClass);
  }

  private void getCarsByMark() {
    String markName = clientView.getCarMark();
    List<Car> carsByMarkName = carsService.getCarsByMark(markName);
    clientView.printCars("Cars by mark name: ", carsByMarkName);
  }

}
