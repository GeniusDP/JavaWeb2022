package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.repositories.CarRepository;
import org.example.repositories.MarkRepository;
import org.example.repositories.UserRepository;
import org.example.security.SecurityContext;
import org.example.services.RegisterLoginService;
import org.example.views.registerlogin.RegisterLoginView;

@RequiredArgsConstructor
public class Controller {

  private final CarRepository carRepository;
  private final MarkRepository markRepository;
  private final UserRepository userRepository;

  public void start(){
    if(SecurityContext.getContext().isEmpty()){
      RegisterLoginService registerLoginService = new RegisterLoginService(userRepository);
      RegisterLoginView registerLoginView = new RegisterLoginView();
      RegisterLoginController registerLoginController = new RegisterLoginController(
              registerLoginView,
              registerLoginService
      );
      boolean success = registerLoginController.start();
      System.out.println("success of login/register = " + success);
    }
  }

}
