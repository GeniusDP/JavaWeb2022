package org.example.zaranik.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReadUtil {
    public String readAll(String resultFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(resultFilePath));
        StringBuilder result = new StringBuilder();
        while(scanner.hasNextLine()){
            result
                    .append(scanner.nextLine())
                    .append("\n");
        }
        return result.toString();
    }


}
