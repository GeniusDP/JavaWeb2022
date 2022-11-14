package org.example.controllers.registerlogin;

import lombok.RequiredArgsConstructor;
import org.example.entities.user.Role;
import org.example.exceptions.DatabaseException;
import org.example.services.RegisterLoginService;
import org.example.views.registerlogin.RegisterLoginView;

@RequiredArgsConstructor
public class RegisterLoginController {

  private final RegisterLoginView registerLoginView;
  private final RegisterLoginService registerLoginService;

  public boolean perform() {
    LoginRegisterAction action = registerLoginView.chooseLoginOrRegister();
    try {
      if (action == LoginRegisterAction.LOGIN) {
        return login();
      }
      return register();
    } catch (DatabaseException e) {
      System.out.println("Ooops, operation failed due to some issue.");
      return false;
    }
  }

  private boolean login() {
    registerLoginView.loginStart();
    String username = registerLoginView.getUsername();
    String password = registerLoginView.getPassword();
    return registerLoginService.login(username, password);
  }

  private boolean register() {
    registerLoginView.registrationStart();
    String username = registerLoginView.getUsername();
    String password = registerLoginView.getPassword();
    String firstName = registerLoginView.getFirstName();
    String lastName = registerLoginView.getLastName();
    return registerLoginService.registerClient(username, password, firstName, lastName, Role.CLIENT);
  }

}
