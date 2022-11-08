package org.example.controllers.main;

import lombok.RequiredArgsConstructor;
import org.example.controllers.registerlogin.RegisterLoginController;
import org.example.repositories.CarRepository;
import org.example.repositories.MarkRepository;
import org.example.repositories.UserRepository;
import org.example.security.SecurityContext;
import org.example.services.RegisterLoginService;
import org.example.views.main.MainView;
import org.example.views.registerlogin.RegisterLoginView;

@RequiredArgsConstructor
public class Controller {

  private final CarRepository carRepository;
  private final MarkRepository markRepository;
  private final UserRepository userRepository;
  private final MainView mainView;

  public void start() {
    authorize();
    switch (SecurityContext.getContext().getSubject().getRole()) {
      case CLIENT -> actAsClient();
      case MANAGER -> actAsManager();
      case ADMIN -> actAsAdmin();
    }
  }

  private void actAsAdmin() {

  }

  private void actAsManager() {

  }

  private void actAsClient() {

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
