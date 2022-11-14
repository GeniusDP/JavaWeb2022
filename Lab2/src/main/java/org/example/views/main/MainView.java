package org.example.views.main;

import java.util.Scanner;

public class MainView {

  private final Scanner scanner = new Scanner(System.in);

  public void unauthorizedMessage() {
    System.out.println("Bad input data: performing login/register operation was NOT SUCCESSFUL. May be, you are banned.");
  }

  public boolean askForRepeat() {
    System.out.println("Go to menu another one time? Write YES of NO.");
    return scanner.nextLine().equalsIgnoreCase("YES");
  }

}
