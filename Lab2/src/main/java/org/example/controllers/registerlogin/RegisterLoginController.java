package org.example.controllers.registerlogin;

import lombok.RequiredArgsConstructor;
import org.example.services.RegisterLoginService;
import org.example.views.registerlogin.LoginRegisterAction;
import org.example.views.registerlogin.RegisterLoginView;

@RequiredArgsConstructor
public class RegisterLoginController {

  private final RegisterLoginView registerLoginView;
  private final RegisterLoginService registerLoginService;

  public boolean perform() {
    LoginRegisterAction action = registerLoginView.chooseLoginOrRegister();
    if (action == LoginRegisterAction.LOGIN) {
      return login();
    }
    return register();
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
    return registerLoginService.registerClient(username, password, firstName, lastName);
  }

}
