package org.example.zaranik.controllers;

import org.example.zaranik.services.ValidatorService;
import org.example.zaranik.utils.FileReadUtil;
import org.example.zaranik.services.FolderLetterCounter;
import org.example.zaranik.views.View;

//    /home/zaranik/Рабочий стол/Projects/firstProject/src/main/resources/folder1
//    C:\Users\Asus\Desktop\lab1
public class MainController {

    public static void main(String[] args) throws Exception {
        View view = new View();
        ValidatorService validatorService = new ValidatorService();

        do{
            String inputLetter = view.getChar();
            if (!validatorService.charIsValid(inputLetter)) {
                view.printCharIsNotValidError();
                continue;
            }

            char letter = inputLetter.charAt(0);
            String dirPath = view.getPath();
            if (!validatorService.dirPathIsValid(dirPath)) {
                view.printDirPathIsNotValidError();
                continue;
            }

            FolderLetterCounter flc = new FolderLetterCounter(dirPath);
            String resultFilePath = flc.count(letter);

            view.printResultFilePath(resultFilePath);

            FileReadUtil fileReadUtil = new FileReadUtil();
            String result = fileReadUtil.readAll(resultFilePath);

            view.printMessage(result);
        }while(view.nextStep());
    }

}
