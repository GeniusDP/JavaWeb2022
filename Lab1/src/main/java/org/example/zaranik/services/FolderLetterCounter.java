package org.example.zaranik.services;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public final class FolderLetterCounter {
    private final String dirPath;

    public FolderLetterCounter(String dirPath) {
        this.dirPath = dirPath;
    }

    public String count(char letter) throws Exception {
        File file = new File(this.dirPath);
        ExecutorService executorService = Executors.newCachedThreadPool();

        String resultFilePath = "src/main/resources/" + UUID.randomUUID() + ".txt";
        PrintWriter printWriter = new PrintWriter( resultFilePath );
        countLettersInFile(file, letter, executorService, printWriter);

        executorService.shutdown();
        printWriter.close();
        return resultFilePath;
    }

    private int countLettersInFile(File file, char letter, ExecutorService executorService, PrintWriter pw) throws Exception {
        if (file.isDirectory()) {
            File[] allFiles = file.listFiles();
            List<Future<Integer>> allTasks = new ArrayList<>();
            for (var currentFile : allFiles) {
                Callable<Integer> recursiveTask = () -> countLettersInFile(currentFile, letter, executorService, pw);
                Future<Integer> submit = executorService.submit(recursiveTask);
                allTasks.add(submit);
            }
            int result = 0;
            for (var task : allTasks) {
                result += task.get();
            }
            return result;
        }
        if(!file.getName().endsWith(".txt")){
            return 0;
        }
        int cnt = (int) Files.lines(file.toPath())
                .flatMap(string -> Arrays.stream(string.split("\\s")))
                .filter(string -> string.startsWith(Character.toString(letter)))
                .count();
        pw.println(file.getAbsolutePath() + " ====> " + cnt);
        pw.flush();
        return cnt;
    }

}
