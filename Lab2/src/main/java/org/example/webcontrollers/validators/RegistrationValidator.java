package org.example.webcontrollers.validators;

public class RegistrationValidator {

  public boolean validate(String username, String password, String repeatPassword, String firstName, String lastName) {
    return username != null && password != null && firstName != null && lastName != null
      && password.equals(repeatPassword);
  }

}
