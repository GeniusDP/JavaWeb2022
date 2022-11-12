package org.example.controllers.client;

import lombok.RequiredArgsConstructor;
import org.example.entities.Car;
import org.example.services.CarsService;
import org.example.views.client.ClientView;

import java.util.List;

@RequiredArgsConstructor
public class ClientController {

  private final ClientView clientView;
  private final CarsService carsService;

  public void start() {
    ClientAction action = clientView.chooseAction();
    switch (action) {
      case SHOW_CARS_BY_MARK -> getCarsByMark();
      case SHOW_CARS_BY_CLASS -> getCarsByClass();
      case SORT_CARS_BY_PRICE -> sortCarsByPrice();
      case SORT_CARS_BY_NAME -> sortCarsByName();
    }
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
