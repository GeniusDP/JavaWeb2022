package org.example.zaranik.services;

import java.io.File;

public class Validator {
    public boolean charIsValid(String inputLetter) {
        return inputLetter != null && inputLetter.matches("[A-Za-zА-Яа-я]");
    }

    public boolean dirPathIsValid(String dirPath) {
        File file = new File(dirPath);
        return file.exists() && file.isDirectory();
    }
}
