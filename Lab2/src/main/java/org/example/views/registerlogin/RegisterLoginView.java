package org.example.views.registerlogin;

import java.util.Scanner;

import static org.example.views.registerlogin.LoginRegisterAction.LOGIN;
import static org.example.views.registerlogin.LoginRegisterAction.REGISTER;

public class RegisterLoginView {

    private final Scanner scanner = new Scanner(System.in);

    public LoginRegisterAction chooseLoginOrRegister() {
        LoginRegisterAction action;
        do{
            System.out.println("Print LOGIN to login or REGISTER to register:");
            String line = scanner.nextLine();
            action = LoginRegisterAction.getAction(line);
        } while (action != LOGIN && action != REGISTER);
        return action;
    }

    public void loginStart() {
        System.out.println("LOGGING IN");
    }

    public void registrationStart() {
        System.out.println("REGISTRATION");
    }

    public String getUsername() {
        System.out.println("enter your username and press enter: ");
        return scanner.nextLine();
    }

    public String getPassword() {
        System.out.println("enter your password and press enter: ");
        return scanner.nextLine();
    }

    public String getFirstName() {
        System.out.println("enter your first name and press enter: ");
        return scanner.nextLine();
    }

    public String getLastName() {
        System.out.println("enter your last name and press enter: ");
        return scanner.nextLine();
    }

}
