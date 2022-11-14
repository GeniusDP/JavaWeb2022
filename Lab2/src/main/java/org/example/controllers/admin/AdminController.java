package org.example.controllers.admin;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.entities.car.Car;
import org.example.entities.car.Mark;
import org.example.entities.car.QualityClass;
import org.example.entities.user.Role;
import org.example.exceptions.DatabaseException;
import org.example.services.CarsService;
import org.example.services.MarkService;
import org.example.services.RegisterLoginService;
import org.example.services.UserService;
import org.example.views.admin.AdminView;

@RequiredArgsConstructor
public class AdminController {

  private final AdminView adminView;
  private final CarsService carsService;
  private final RegisterLoginService registerLoginService;
  private final UserService userService;
  private final MarkService markService;

  public void start() {
    AdminAction action = adminView.chooseAction();
    switch (action) {
      case BAN_USER -> banUser();
      case UNBAN_USER -> unbanUser();
      case REGISTER_MANAGER -> registerManager();
      case SHOW_ALL_CARS -> showAllCars();
      case CREATE_CAR -> createCar();
      case REMOVE_CAR -> removeCar();
    }
  }

  private void banUser() {
    try {
      adminView.banningUserStart();
      long userId = adminView.getUserId();
      if (userService.existsById(userId)) {
        userService.banUser(userId);
        adminView.banningSuccessful();
      } else {
        adminView.noSuchUserFound();
      }
    } catch (DatabaseException e) {
      System.out.println("Ooops, 'banning user' operation failed due to some issue.");
    }
  }

  private void unbanUser() {
    try {
      adminView.unbanningUserStart();
      long userId = adminView.getUserId();
      if (userService.existsById(userId)) {
        userService.unbanUser(userId);
        adminView.unbanningSuccessful();
      } else {
        adminView.noSuchUserFound();
      }
    } catch (DatabaseException e) {
      System.out.println("Ooops, 'unbanning user' operation failed due to some issue.");
    }
  }

  private void registerManager() {
    try {
      adminView.registrationStart();
      String username = adminView.getUsername();
      String password = adminView.getPassword();
      String firstName = adminView.getFirstName();
      String lastName = adminView.getLastName();
      registerLoginService.registerUser(username, password, firstName, lastName, Role.MANAGER);
      adminView.managerRegistrationSuccessful();
    } catch (DatabaseException e) {
      System.out.println("Ooops, 'register manager' operation failed due to some issue.");
    }

  }

  private void showAllCars() {
    try {
      List<Car> cars = carsService.getCarsSortedByPrice();
      adminView.printCars("Cars by mark name: ", cars);
    } catch (DatabaseException e) {
      System.out.println("Ooops, 'show all cars' operation failed due to some issue.");
    }
  }

  private void createCar() {
    try {
      adminView.creatingCarStart();
      String carName = adminView.getCarName();
      if (!carsService.existsByName(carName)) {
        long markId = adminView.getMarkId();
        if (markService.existsById(markId)) {
          Mark mark = markService.findById(markId);
          int basePrice = adminView.getBasePrice();
          QualityClass qualityClass = adminView.getQualityClass();

          Car car = Car.builder()
              .name(carName)
              .basePrice(basePrice)
              .mark(mark)
              .qualityClass(qualityClass)
              .build();
          carsService.createCar(car);
          adminView.creatingCarSuccessfully();
        } else {
          adminView.noSuchMarkFound();
        }
      } else {
        adminView.carAlreadyExists();
      }
    } catch (DatabaseException e) {
      System.out.println("Ooops, 'removing car' operation failed due to some issue.");
    }
  }

  private void removeCar() {
    try {
      adminView.removingCarStart();
      long carId = adminView.getCarId();
      if (carsService.existsById(carId)) {
        carsService.removeCarById(carId);
        adminView.removingCarSuccessfully();
      } else {
        adminView.noSuchCarFound();
      }
    } catch (DatabaseException e) {
      System.out.println("Ooops, 'removing car' operation failed due to some issue.");
    }
  }
}
