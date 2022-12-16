package org.example.webcontrollers.validators;

public class LoginValidator {

  public boolean validate(String username, String password) {
    return username != null && password != null;
  }

}
