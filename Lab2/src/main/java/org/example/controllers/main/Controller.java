package org.example.controllers.main;

import lombok.RequiredArgsConstructor;
import org.example.controllers.client.ClientController;
import org.example.controllers.manager.ManagerController;
import org.example.controllers.registerlogin.RegisterLoginController;
import org.example.repositories.CarRepository;
import org.example.repositories.MarkRepository;
import org.example.repositories.ReceiptRepository;
import org.example.repositories.UserRepository;
import org.example.security.SecurityContext;
import org.example.services.CarsService;
import org.example.services.ReceiptService;
import org.example.services.RegisterLoginService;
import org.example.views.client.ClientView;
import org.example.views.main.MainView;
import org.example.views.manager.ManagerView;
import org.example.views.registerlogin.RegisterLoginView;

@RequiredArgsConstructor
public class Controller {

  private final CarRepository carRepository;
  private final MarkRepository markRepository;
  private final UserRepository userRepository;
  private final ReceiptRepository receiptRepository;
  private final MainView mainView;

  public void start() {
    authorize();
    do {
      switch (SecurityContext.getContext().getSubject().getRole()) {
        case CLIENT -> actAsClient();
        case MANAGER -> actAsManager();
        case ADMIN -> actAsAdmin();
      }
    } while (mainView.askForRepeat());

  }

  private void actAsAdmin() {

  }

  private void actAsManager() {
    ManagerController managerController = new ManagerController(
        new ManagerView(),
        new ReceiptService(receiptRepository)
    );
    managerController.start();
  }

  private void actAsClient() {
    ClientController clientController = new ClientController(
        new ClientView(),
        new CarsService(carRepository),
        new ReceiptService(receiptRepository)
    );
    clientController.start();
  }

  private void authorize() {
    if (SecurityContext.getContext().isEmpty()) {
      RegisterLoginService registerLoginService = new RegisterLoginService(userRepository);
      RegisterLoginView registerLoginView = new RegisterLoginView();
      RegisterLoginController registerLoginController = new RegisterLoginController(
          registerLoginView,
          registerLoginService
      );
      do {
        if (!registerLoginController.perform()) {
          mainView.unauthorizedMessage();
        }
      } while (SecurityContext.getContext().isEmpty());
    }
  }

}
