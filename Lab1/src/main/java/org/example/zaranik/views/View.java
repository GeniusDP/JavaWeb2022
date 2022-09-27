package org.example.zaranik.views;

import org.example.zaranik.utils.ColorfulPrinter;

import java.util.Scanner;

import static org.example.zaranik.utils.Color.*;
import static org.example.zaranik.utils.Color.ANSI_YELLOW;

public class View {
    public String getChar() {
        Scanner scanner = new Scanner(System.in);
        ColorfulPrinter.printColorfullyAndReset(ANSI_GREEN, "Input char please: ");
        return scanner.nextLine();
    }

    public String getPath() {
        Scanner scanner = new Scanner(System.in);
        ColorfulPrinter.printColorfullyAndReset(ANSI_GREEN, "Input absolute dir path please: ");
        return scanner.nextLine();
    }

    public void printCharIsNotValidError() {
        ColorfulPrinter.printlnColorfullyAndReset(ANSI_RED, "ERROR: Char is not valid:(");
    }

    public void printDirPathIsNotValidError() {
        ColorfulPrinter.printlnColorfullyAndReset(ANSI_RED, "ERROR: Dir path is not valid:(");
    }

    public void printResult(int count) {
        if(count == 0){
            ColorfulPrinter.printlnColorfullyAndReset(ANSI_YELLOW, "Search performed successfully, but no such words found.");
            return;
        }
        ColorfulPrinter.printlnColorfullyAndReset(ANSI_GREEN, "SUCCESS!");
        ColorfulPrinter.printlnColorfullyAndReset(ANSI_GREEN, "Result is: " + count );
    }


    public void printMessage(String value) {
        ColorfulPrinter.printlnColorfullyAndReset(ANSI_CYAN, value);
    }

    public boolean nextStep() {
        String askMessage = "Do you want to perform anther one step? If yes - print y or whatever you want otherwise: ";
        Scanner scanner = new Scanner(System.in);
        ColorfulPrinter.printColorfullyAndReset(ANSI_PURPLE, askMessage);
        String answer = scanner.nextLine();
        return answer.equals("y");
    }

    public void printResultFilePath(String resultFilePath) {
        printMessage("Results stored in file: " + resultFilePath);
    }
}
